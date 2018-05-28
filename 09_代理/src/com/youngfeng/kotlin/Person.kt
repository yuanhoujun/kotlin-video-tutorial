package com.youngfeng.kotlin

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-28 11:48
 */
class Person {

    var name: String by DelegateLoader()

    val age by lazy(LazyThreadSafetyMode.NONE) {
        println("Visit age...")
        18
    }

    var gender: String by Delegates.observable("Female") { prop, oldValue, newValue ->
        println("$oldValue : $newValue")
    }
}

class Delegate {
    private var value: String? = null

    operator fun getValue(person: Person, property: KProperty<*>): String {
        if (null == value) return ""
        return value!!
    }

    operator fun setValue(person: Person, property: KProperty<*>, s: String) {
        value = s
    }
}

// provideDelegate
class DelegateLoader {
    operator fun provideDelegate(thisRef: Person, property: KProperty<*>): Delegate {
        return Delegate()
    }
}

class User(map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
}

fun main(args: Array<String>) {
    val person = Person()
    person.name = "Scott Smith"
    println(person.name)

    println(person.age)
    println(person.age)

    person.gender = "Male"

    val user = User(mapOf("name" to "Scott Smith", "age" to 30))
    println("${user.name}: ${user.age}")
}