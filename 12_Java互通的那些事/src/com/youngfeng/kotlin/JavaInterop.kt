
@file:JvmName("JavaInterop")

package com.youngfeng.kotlin

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-31 11:17
 */


data class Person(var name: String, var age: Int) {
    @JvmField var isFemale: Boolean = false
}

class Tank {
    var name: String = ""

    companion object {
        @JvmField
        var MOVED = false

        @JvmStatic
        fun create(): Tank {
            return Tank()
        }
    }
}

class Provider {}

object Singleton {
    lateinit var provider: Provider
    // 使用const修饰的成员变量不会生成对应的get方法
    const val CREATED = false
}

fun foo() {
    println("Hello, world")
}

@JvmName("filterValidString")
fun List<String>.filterValid(): List<String>? {
    return null
}

@JvmName("filterValidInt")
fun List<Int>.filterValid(): List<Int>? {
    return null
}

@JvmOverloads
fun dim(enable: Boolean = true, value: Float = 0.8f) {}

interface R1 {
    fun foo()
}

val r2 = Client.R2 { println("R2 <<<") }