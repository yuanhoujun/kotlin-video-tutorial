package com.youngfeng.kotlin

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-29 11:00
 */
data class Person(var name: String, var age: Int) {
}

fun main(args: Array<String>) {
    val person = Person("Scott Smith", 30)
    val (name, age) = person
    println("name = $name, age = $age")

    val person2 = Person("Lily", 25)

    val list = listOf(person, person2)

    for ((name, _) in list) {
        println("name = $name")
    }

    val map = mapOf("k1" to "v1", "k2" to "v2")
    for ((key, value) in map) {
        println("key = $key, value = $value")
    }

    map.mapValues { (key, value) ->
        println("$key : $value")
    }
}