

fun main(args: Array<String>) {
	val point = object {
		var x: Int = 0
		var y: Int = 0
	}

	println(point.x)
}

class Bar {

	fun foo() = object {
		var x: Int = 1
	}


	private fun foo1() = object {
		var x: Int = 1
	}

	fun baz() {
		// val x1 = foo().x
		val x2 = foo1().x
	}
}