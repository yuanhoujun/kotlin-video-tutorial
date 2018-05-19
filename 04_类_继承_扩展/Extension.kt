
open class A {
	open var x: Int = 10


}

class A1: A() {

}



val A.y: Int 
	get() = 100

val A.x: Int 
	get() = 110

fun <T> T.sayHello(name: String) {
	println("Hello, $name")
}

// DR: Dispatch Receiver, ER: Extension Receiver
// ER：静态解析，接收者类型由声明时决定
// DR：动态解析的，接收类型由运行时决定

// A称之为printX的接收者(Receiver)
fun A.printX() {
	println("x = ${this.x}")
}

fun A1.printX() {
	println("x = ${this.x} in A1")
}

fun f(a: A) {
	a.printX()
}


open class C {}
class C1: C() {

}

open class D {

	open fun C.foo() {
		println("foo in C, declared in D")
	}

	open fun C1.foo() {
		println("foo in C1, declared in D")
	}

	fun call(c: C) {
		c.foo()
	}
}

class D1: D() {

	override fun C.foo() {
		println("foo in C, declared in D1")
	}

	override fun C1.foo() {
		println("foo in C1, declared in D1")
	}
}


fun main(arg: Array<String>) {
	val a = A()
	a.printX()

	println(a.y)


	// DR: D, ER: C
	D().call(C())

	// DR: D, ER: C1
	D().call(C1())

	// DR: D1, ER: C
	D1().call(C())
}