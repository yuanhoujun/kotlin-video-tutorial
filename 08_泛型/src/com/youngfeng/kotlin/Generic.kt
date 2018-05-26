package com.youngfeng.kotlin

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-26 20:29
 */

// 声明区变量
interface Collection<in T> {

    fun add(item: T)

//    fun get(index: Int): T
}

// out: 协变 => 生产者 => ? extends T
// in：逆变 => 消费者
// out: 返回值位置，in：参数位置

interface Array2<T> {
    fun set(index: Int, item: T)
    fun get(index: Int): T
}

// 使用区变量
fun copy(from: Array2<out Any>, to: Array2<Any>) {
}

fun fill(arr: Array2<in String>, value: String) {

}

open class A {}
open class B: A()

fun <T: A> foo(data: T) {}

interface C {}
open class D {}

// T：实现接口C，并且继承类D
fun <T> bar(data: T)
    where T: C,
          T: D {

}

open class TUpper {
    fun f() {}
}

open class TUpper2: TUpper()

class Foo<T: TUpper> {

    fun get(): T {
    }

    fun set(data: T) {}
}

fun main(args: kotlin.Array<String>) {
//    val from: Array2<Int>? = null
//    val to: Array2<Any>? = null
//
//    copy(from!!, to!!)
//
//    val arr: Array2<Any>? = null
//    fill(arr!!, "")
//
//    foo(B())

    val foo: Foo<*> = Foo<TUpper2>()
    val t = foo.get()
    t.f()
    foo.set(TUpper())
//    val t = foo.get()
//    t.f()
}