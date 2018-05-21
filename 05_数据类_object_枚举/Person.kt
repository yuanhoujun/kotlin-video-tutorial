data class Person(var age: Int = 0, var name: String = "") {

}


fun main(args: Array<String>) {
	val person = Person(28, "Scott Smith")
	val (_, name) = person
	println("$name")
}