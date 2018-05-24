
fun main(args: Array<String>) {
	val list = listOf("b", "a", "c")

	val mutableList = mutableListOf("b", "a", "c")
	mutableList.add("d")

	println(mutableList.filter { it != "a" })

	println(mutableList.filterIndexed { index, e -> index > 0 })

	println(mutableList.slice(0..2))

	println(mutableList.take(2))

	println(mutableList.sorted())

	val m1 = mutableList.associateBy({ e -> "$e@"}) { it }
	println(m1)

	val list2 = mutableListOf("abc", "def")
	println(list2.flatMap { it.toList() }.map { "$it@" })

	println(list2.map { "$it@"})

	val list3 = mutableListOf(2, 1, 3, 4)

	println(list3.groupBy { if (it % 2 == 0) "偶数" else "奇数" })

	val list4 = mutableListOf(3, 1, 5, 6)

	// 交集
	val s1 = list3 intersect list4
	println(s1)

	// 并集
	val s2 = list3 union list4
	println(s2)

	// 子集
	val s3 = list3 subtract list4
	println(s3)

	// 子集的补集
	val s4 = list4 subtract list3
	println(s4)

	// 交集的补集
	val s5 = s3 union s4
	println(s5)

	// 并集
	val s21 = list3 + list4
	println(s21)

	// 子集
	val s31 = list3 - list4
	println(s31)
	val s41 = list4 - list3
	println(s41)

	val s51 = s31 + s41
	println(s51)
}