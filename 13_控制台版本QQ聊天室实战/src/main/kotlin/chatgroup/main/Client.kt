package chatgroup.main

import chatgroup.config.ClientConfig
import chatgroup.model.Command
import chatgroup.model.MsgType
import chatgroup.util.BYTE_SIZE_DESC
import chatgroup.util.BYTE_SIZE_TYPE
import chatgroup.util.combine
import chatgroup.util.fileToByteArray
import chatgroup.util.log
import chatgroup.util.parseInputStream
import chatgroup.util.textToByteArray
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.Scanner
import kotlin.concurrent.thread

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 11:56
 */

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    println("请输入附件存储位置：")
    while (true) {
        val path = scanner.nextLine()
        val file = File(path)
        if (file.isDirectory && file.exists()) {
            ClientConfig.attachPath = path
            break
        } else {
            println("路径无效，请重新输入")
        }
    }

    println("请输入用户名：")
    ClientConfig.username = scanner.nextLine()

    println("请输入服务器地址：")
    val ip = scanner.nextLine()

    println("请输入服务器端口")
    val port = scanner.nextInt()

    connect(ip, port) { socket ->
        sendOrReceive(socket)
    }
}

fun connect(ip: String, port: Int, success: (socket: Socket) -> Unit) {
    try {
        val socket = Socket(ip, port)
        success(socket)
    } catch (e: Exception) {
        println(e.message)
    }
}


fun sendOrReceive(socket: Socket) {
    thread {
        processOutputStream(socket.getOutputStream())
    }

    thread {
        processInputStream(socket.getInputStream())
    }
}

fun processOutputStream(outputStream: OutputStream) {
    sendOnline(ClientConfig.username!!, outputStream = outputStream)

    val scanner = Scanner(System.`in`)

    while (true) {
        println("当前输入模式 [${ClientConfig.inputMode.desc()}]")

        var input = scanner.nextLine()

        if (isCmd(input)) {
            ClientConfig.inputMode = Command.convertToCmd(input)

            println("当前输入模式已切换至：${ClientConfig.inputMode.desc()}")

            if (ClientConfig.inputMode == Command.ATTACH) {
                sendCmd(cmd = Command.ATTACH,
                        sender = ClientConfig.username ?: "--",
                        outputStream = outputStream)
            }

            if (ClientConfig.inputMode == Command.DOWN) {
                val index = input.substring(":down#".length)
                sendCmd(cmd = Command.DOWN,
                        sender = ClientConfig.username ?: "--",
                        index = index,
                        outputStream = outputStream)
            }

            if (ClientConfig.inputMode == Command.OFFLINE) {
                sendCmd(cmd = Command.OFFLINE,
                        sender = ClientConfig.username ?: "--",
                        outputStream = outputStream)
            }

            input = null
        }


        if (ClientConfig.inputMode == Command.ATTACH
            || ClientConfig.inputMode == Command.DOWN
            || ClientConfig.inputMode == Command.OFFLINE) {
            ClientConfig.inputMode = Command.TEXT
            continue
        }

        if (ClientConfig.inputMode == Command.FILE) {

            while (true) {
                if (input == null) {
                    println("请输入文件路径")
                    input = scanner.nextLine()
                }

                val file = File(input)
                if (!file.exists() || file.isDirectory) {
                    println("文件路径无效，请重新输入")
                    input = scanner.nextLine()
                } else {
                    sendFile(file, ClientConfig.username ?: "--", file.name, outputStream)
                    break
                }
            }

            ClientConfig.inputMode = Command.TEXT
            continue
        }

        if (ClientConfig.inputMode == Command.TEXT) {
            sendText(input, ClientConfig.username ?: "--", outputStream)
        }
    }
}

fun processInputStream(inputStream: InputStream) {
    while (true) {
        if (inputStream.available() > 0) {
            val msg = parseInputStream(inputStream)

            log("客户端接收到的消息：$msg")

            when (msg.type) {
                MsgType.TEXT -> println(String(msg.data))
                MsgType.FILE -> {
                    val filename = msg.info["filename"]
                    val fos = FileOutputStream("${ClientConfig.attachPath}/$filename")
                    fos.write(msg.data)
                    fos.flush()
                    fos.close()

                    println("附件已经下载成功: ${ClientConfig.attachPath}/$filename")
                }

                MsgType.OFFLINE -> {
                    quit()
                }
            }
        }
    }
}

fun quit() {
    System.exit(0)
}

private fun isCmd(input: String): Boolean {
    if (input.startsWith(":down")) {
        if (input.matches(Regex(":down#[0-9]*"))) return true
    } else {
        return Command.values().any { it.desc == input.trim() }
    }

    return false
}

fun sendMsg(type: String, info: Map<String, String>, data: ByteArray, outputStream: OutputStream) {
    val typeArr = textToByteArray(type, BYTE_SIZE_TYPE)

    var infoStr = info.map { (key, value) -> "$key=$value" }.joinToString(separator = "") { "$it&" }

    if (infoStr.endsWith("&")) {
        infoStr = infoStr.substring(0, infoStr.length - 1)
    }

    val infoArr = textToByteArray(infoStr, BYTE_SIZE_DESC)

    val combineArr = combine(typeArr, infoArr, data)
    outputStream.write(combineArr)
    outputStream.flush()
}

fun sendText(text: String, sender: String, outputStream: OutputStream) {
    sendMsg(MsgType.TEXT.toString(), mapOf("sender" to sender), textToByteArray(text), outputStream)
}

fun sendFile(file: File, sender: String, filename: String, outputStream: OutputStream) {
    sendMsg(MsgType.FILE.toString(), mapOf("sender" to sender, "filename" to filename), fileToByteArray(file), outputStream)
}

// 发送操作指令（:attach, :down#1, :offline， :online)
fun sendCmd(cmd: Command, sender: String, index: String = "-1", outputStream: OutputStream) {
    sendMsg(cmd.value(), mapOf("sender" to sender, "index" to index), byteArrayOf(), outputStream)
}

fun sendOnline(sender: String, index: String = "-1", outputStream: OutputStream) {
    sendMsg(MsgType.ONLINE.type, mapOf("sender" to sender, "index" to index), byteArrayOf(), outputStream)
}