class Outer {

	fun foo() {
		println("Outer foo()")
	}

	inner class Inner {

		fun f() {
			foo()
		}
	}
}

// 嵌套类
// val inner = Outer.Inner()
// 内部类
val inner = Outer().Inner()

// {id: xx, name: xx} => Person() => 数据类