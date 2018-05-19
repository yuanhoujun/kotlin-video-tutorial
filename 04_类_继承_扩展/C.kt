open class C {
	open var y: Int = 0

	constructor(x: Int) {
		println("The secondary constructor, $x")
	}

	init {
		println("This is a init block")
	}

	open fun foo() {
		println("foo in C")
	}
}

class C1(x: Int): C(x) {
	override var y: Int = super.y

	override fun foo() {
		println("foo in C1")
	}

	inner class CInner {

		fun f() {
			super@C1.foo()
		}
	}
}

fun main(args: Array<String>) {
	val c: C = C1(10)

	c.foo()
}