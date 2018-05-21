
enum class Direction {
	EAST, SOUTH, WEST, NORTH
}


enum class Operation(x: Int) {
	ADD(1) {
		override fun action() = 1
	},
	MINUS(2) {
		override fun action() = 2
	};

	abstract fun action(): Int
}

fun main(args: Array<String>) {
	println(enumValues<Direction>().joinToString { "${it.ordinal} : ${it.name}"})
}