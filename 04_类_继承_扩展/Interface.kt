
interface Moveable {

	fun move() 

	fun defaultImpl() {
		println("Moveable default implementation... ")
	}
}

class Tank: Moveable {

	override fun move() {
		println("Tank moving...")
	}
}

interface A {

	fun foo() {
		println("foo in A")
	}

	fun f()
}

interface B {

	fun foo() {
		println("foo in B")
	}

	fun f() 
}

class C: A, B {

	override fun f() {
		super<A>.foo()
		super<B>.foo()
	}

	override fun foo() {
		
	}
}


fun main(args: Array<String>) {
	val m: Moveable = Tank()

	m.move()
	m.defaultImpl()

	C().f()
}