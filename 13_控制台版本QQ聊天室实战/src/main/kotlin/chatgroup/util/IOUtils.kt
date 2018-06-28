package chatgroup.util

import chatgroup.model.Msg
import chatgroup.model.MsgType
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 14:53
 */

/**
 * 约定：类型（20字节） + 描述（50个字节）+ 数据（文本、文件、命令等）
*/

val BYTE_SIZE_TYPE = 20
val BYTE_SIZE_DESC = 50

fun textToByteArray(text: String, size: Int = -1): ByteArray {
    val textByteArr = text.toByteArray()

    if (textByteArr.size < size) {
        return textByteArr.copyOf(size)
    }

    return textByteArr
}


fun fileToByteArray(file: File): ByteArray {
    val fis = FileInputStream(file)
    val fileByteArr = fis.readBytes()
    fis.close()

    return fileByteArr
}

fun combine(vararg byteArr: ByteArray): ByteArray {
    var arr = byteArrayOf()

    byteArr.forEach { arr += it }

    return arr
}

fun parseInputStream(inputStream: InputStream): Msg {
    var buffer = ByteArray(BYTE_SIZE_TYPE)
    inputStream.read(buffer)

    val type = String(buffer.filter { it.toInt() != 0 }.toByteArray())

    val msgType = MsgType.convertToMsgType(type)

    buffer = ByteArray(BYTE_SIZE_DESC)
    inputStream.read(buffer)

    val desc = String(buffer.filter { it.toInt() != 0 }.toByteArray())

    val info = mutableMapOf<String, String>()

    desc.split("&").map {
        it.split("=")
    }.filter { it.size > 1 }
        .forEach { info[it[0]] = it[1] }

    buffer = ByteArray(inputStream.available())
    inputStream.read(buffer)

    return Msg(msgType, info, buffer)
}

//fun main(args: Array<String>) {
//    val type = "file"
//    val desc = "This is a file..."
//    val path = "/Users/ouyangfeng/Desktop/ChatGroup/wx.png"
//
//    val typeArr = textToByteArray(type, BYTE_SIZE_TYPE)
//    val descArr = textToByteArray(desc, BYTE_SIZE_DESC)
//    val fileArr = fileToByteArray(File(path))
//
//    val byteArr = combine(typeArr, descArr, fileArr)
//    val bis = ByteArrayInputStream(byteArr)
//
//    var buffer = ByteArray(BYTE_SIZE_TYPE)
//    bis.read(buffer)
//
//    println(String(buffer.filter { it.toInt() != 0 }.toByteArray()))
//
//    buffer = ByteArray(BYTE_SIZE_DESC)
//    bis.read(buffer)
//
//    println(String(buffer.filter { it.toInt() != 0 }.toByteArray()))
//
//    val fos = FileOutputStream("/Users/ouyangfeng/Desktop/ChatGroup/wx110.png")
//    buffer = ByteArray(8 * 1024)
//
//    var read = bis.read(buffer)
//
//    while (read > 0) {
//        fos.write(buffer, 0, read)
//        read = bis.read(buffer)
//    }
//
//    fos.flush()
//    fos.close()
//    bis.close()
//}