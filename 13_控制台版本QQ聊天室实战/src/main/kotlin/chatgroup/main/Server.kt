package chatgroup.main

import chatgroup.config.ServerConfig
import chatgroup.model.Command
import chatgroup.model.MsgType
import chatgroup.util.log
import chatgroup.util.parseInputStream
import chatgroup.util.textToByteArray
import java.io.File
import java.io.FileOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.Scanner

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 11:56
 */

fun main(args: Array<String>) {
    start()
}

fun start() {
    println("请输入附件存储位置: ")

    val scanner = Scanner(System.`in`)

    while (true) {
        val path = scanner.nextLine()
        val file = File(path)
        if (file.exists() && file.isDirectory) {
            ServerConfig.attachPath = path
            File(ServerConfig.attachPath).listFiles().filter { it.isFile }.forEach {
                ServerConfig.attachList.add(it.absolutePath)
            }
            break
        } else {
            println("路径无效，请重新输入")
        }
    }


    val ss = ServerSocket(8888)
    println("服务器已经启动成 <<< ")

    while (true) {
        val socket = ss.accept()
        println("有新用户上线啦！")

        val connector = ClientConnector.create(socket)
        ServerConfig.connectors.add(connector)
        connector.start()
    }
}

class ClientConnector(val socket: Socket): Thread() {

    companion object {
        fun create(socket: Socket) = ClientConnector(socket)
    }

    fun sendText(text: String) {
        sendMsg(MsgType.TEXT.toString(), mapOf(), textToByteArray(text), socket.getOutputStream())
    }

    fun sendFile(file: File) {
        chatgroup.main.sendFile(file, "系统消息", file.name, socket.getOutputStream())
    }

    override fun run() {
        super.run()

        loop@ while (true) {
            val inputStream = socket.getInputStream()
            val msg = parseInputStream(inputStream)
            println(msg)

            when (msg.type) {
                MsgType.TEXT -> {
                    val text = String(msg.data)
                    val sender = msg.info["sender"]
                    ServerConfig.connectors.forEach { it.sendText("[$sender] $text") }
                }
                MsgType.FILE -> {
                    val filename = msg.info["filename"]
                    val fos = FileOutputStream("${ServerConfig.attachPath}/$filename")
                    fos.write(msg.data)
                    fos.flush()
                    fos.close()

                    ServerConfig.attachList.add("${ServerConfig.attachPath}/$filename")

                    sendText("附件已经上传成功： ${ServerConfig.attachPath}/$filename")
                }
                MsgType.ATTACH -> {
                    val attachStr = ServerConfig.attachList
                                                .mapIndexed { index, s -> "${index + 1}、$s" }
                                                .joinToString(separator = "") { "$it\n" }
                    sendText(attachStr)
                }
                MsgType.DOWN -> {
                    val index = (msg.info["index"]?.toInt() ?: -1) - 1
                    if (index < 0 || index > ServerConfig.attachList.size - 1) {
                        sendText("索引无效，正确取值：[:down#1 ~ :down#${ServerConfig.attachList.size}]")
                    } else {
                        log(ServerConfig.attachList[index])
                        sendFile(File(ServerConfig.attachList[index]))
                    }
                }

                MsgType.OFFLINE -> {
                    sendCmd(cmd = Command.OFFLINE, sender = "系统消息", outputStream = socket.getOutputStream())

                    ServerConfig.connectors.remove(this)
                    ServerConfig.connectors.forEach { it.sendText("[系统消息] ${msg.info["sender"]}已下线！！！") }

                    break@loop
                }

                MsgType.ONLINE -> {
                    ServerConfig.connectors.forEach { it.sendText("[系统消息] ${msg.info["sender"]}已上线!!!") }
                }
            }
        }
    }
}