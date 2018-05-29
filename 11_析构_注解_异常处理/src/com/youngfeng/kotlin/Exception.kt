package com.youngfeng.kotlin

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-29 11:46
 */


fun bar(value: Int) {
   if (value < 0) throw IllegalArgumentException()
    if (value % 2 != 0) throw RuntimeException()
}


fun baz(input: String): Int? {
    return try { input.toInt() } catch (e: NumberFormatException) { null }
}

fun bar1() {
    bar(11)
}