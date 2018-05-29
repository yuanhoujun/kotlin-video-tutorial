package com.youngfeng.kotlin

import kotlin.reflect.KProperty

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-29 11:12
 */

@Target(AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.FUNCTION,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Inject(val value: Int)

// name: 1)构造函数参数中，2)成员变量中，3)setter/getter方法中
class Bird(@property:Inject(10) var name: String) {
    @delegate:Inject(10) val age: Int by Delegate {
        10
    }
}

private object UNINITIALIZE_VALUE

class Delegate<T>(init: ()->T) {
    private val initialize: ()->T = init
    private var _value: Any? = UNINITIALIZE_VALUE

    operator fun getValue(bird: Bird, property: KProperty<*>): T {
        if (_value == UNINITIALIZE_VALUE) {
            _value = initialize()
        }
        return _value as T
    }
}

fun @receiver:com.youngfeng.kotlin.Inject(10) Bird.fly() {
    println("I can fly")
}

fun main(args: Array<String>) {
    val property: KProperty<*> = Bird::name
    println(property.annotations)
}