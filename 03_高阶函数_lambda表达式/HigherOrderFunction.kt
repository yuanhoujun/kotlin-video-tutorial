

fun <T> predicate(t: T, condition: (t: T) -> Boolean): Boolean {
	return condition(t)
}

fun isEven(x: Int): Boolean {
	return x % 2 == 0
}

class A {

	fun isEven(x: Int): Boolean {
		return x % 2 == 0
	}
}

class B: (Int) -> Boolean {

	override fun invoke(x: Int): Boolean {
		return x % 2 == 0
	}
}

fun main(args: Array<String>) {
	// 如果lambda表达式只有一个参数, 可以使用内置变量it指代这个唯一参数
	val isEven = predicate(10) { it % 2 == 0 }
	println(isEven)

	val sum: (Int, Int) -> Int = { x, y -> x + y }
	println("1 + 2 = ${sum(1, 2)}")

	println(predicate(10, ::isEven))

	val a = A()
	println(predicate(10, a::isEven))

	val f1 = A::isEven
	println(f1(a, 10))

	val result = { x: Int, y: Int -> x + y } (1, 2)
	println("1 + 2 = $result")

	val f2 = fun(x: Int, y: Int): Int {
		return x + y
	}
	println(f2(1, 2))
	println(f2.invoke(1, 2))
}