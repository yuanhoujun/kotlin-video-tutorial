

fun main(args: Array<String>) {
	var x = 6

	if (x % 2 == 0) {
		println("偶数")
	} else {
		println("奇数")
	}

	println(if (x % 2 == 0) "偶数" else "奇数")

	when (x) {
		1 -> println("x = 1")
		in 2..5 -> println("x > 1 && x <= 5")
		in Int.MIN_VALUE..0 -> println("x < 1")
		in 6..Int.MAX_VALUE -> println("x > 5")
	}

	when {
		x == 1 -> println("x == 1")
		x > 1 && x <= 5 -> println("x > 1 && x <= 5") 
		x < 1 -> println("x < 1")
		x > 5 -> println("x > 5")
	}

	var str = when (x) {
		1 -> "1"
		in 2..Int.MAX_VALUE -> "x > 1"
		else -> "x < 1"
	}

	println(str)

	var list = listOf(2, 1, 3, 4)
	for (a in list) {
		println(a)
	}

	for (i in 9 downTo 1 step 2) {
		println("i = $i")
	}

	for ((index, value) in list.withIndex()) {
		println("$index : $value")
	}

	while (x > 6) {
		println("x > 6")
	}

	do {
			var y = op()
			println("y = $y")
		} while (y > 1)


	var arr1 = arrayOf(1, 2, 3, 4)	
	var arr2 = arrayOf(5, 6, 7, 8)

	for (a in arr1) {
		loop@ for (b in arr2) {
			println("a = $a, b = $b")

			if (a >= 2) {
				break@loop
			}
		}
	}

	val s = sum(1, 2) s@ { x , y ->
		return@s x + y
	}
	println(s)

	// continue
	arr1.forEach loop@ {
		if (it == 2) return@loop
		println("it = $it")
	}

	// break
	run loop@ {
    	arr1.forEach {
			if (it == 2) return@loop
			println("it = $it")
		}
    }	

	println("Loop over")
}

fun sum(x: Int, y: Int, action: (x: Int, y: Int) -> Int): Int {
	return action(x, y)
}

var a = 100

private fun op(): Int {
	a = a / 3
	return a
}