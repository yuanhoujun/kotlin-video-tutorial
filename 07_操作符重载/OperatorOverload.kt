import java.util.*

class Number(var value: Int) {

	operator fun unaryMinus(): Number {
		this.value = -value
		return this
	}

	operator fun inc(): Number {
		return Number(this.value + 1)
	}

	operator fun plus(other: Number): Number {
		return Number(this.value + other.value)
	}

	operator fun rangeTo(other: Number): IntRange {
		return this.value..other.value
	}

	override fun equals(other: Any?): Boolean {
		if (other is Number) return this.value == other?.value
		return false
	}

	operator fun compareTo(other: Number): Int {
		return this.value - other.value
	}
}



class MyList {
	var mList = ArrayList<Int>()

	fun add(value: Int) {
		mList.add(value)
	}

	operator fun get(index: Int): Int {
		return mList.get(index)
	}

	operator fun invoke() {
		println("$this invoked")
	}
}



fun main(args: Array<String>) {
	var number = Number(10)
	val oldNumber = number ++
	println("Old value = ${oldNumber.value}, new value = ${number.value}")

	val number1 = Number(21)
	println((number + number1).value)

	println(number..number1)

	for (x in number..number1) {
		println("x = $x")
	}

	val list = MyList()
	list.add(2)
	list.add(3)
	println(list[0])

	list()

	number.value = number1.value
	println(number == number1)


	number1.value = 21

	println(number > number1)
}

