class Person public constructor(var age: Int, var name: String) {
	
	var gender: Int = -1
		private set
		get() {
			return field
		}

	init {
		println("The primary constructor...")
	}

	init {
		println("This is a init block too")
	}

	constructor(name: String): this(18, name) {
		println("The secondary constructor...")
	}
}


fun main(args: Array<String>) {
	val a = Person(18, "Scott Smith")
	a.age = 28 // a.setAge(28)
	println(a.age) // a.getAge()

	a.gender = 1
}

