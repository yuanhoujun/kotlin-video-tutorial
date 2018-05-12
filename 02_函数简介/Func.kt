fun argsLength(vararg args: String) {
	println("args size = ${args.size}")
}

fun sum(x: Int, y: Int) = x + y

fun canAdd(x: Int): Boolean {
	fun isOdd(x: Int): Boolean {
		return x % 2 != 0
	}

	return isOdd(x)
}

 fun f(n: Int): Int {
	if (n == 1) return 1
	return n * f(n - 1)
}

fun main(args: Array<String>) {
	val arr = arrayOf("a", "b", "c")
	argsLength(*arr)

	println(sum(x = 1, y = 1))
	println(canAdd(10))
}
