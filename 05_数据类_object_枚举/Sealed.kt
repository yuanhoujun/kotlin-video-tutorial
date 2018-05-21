
sealed class Operation(x: Int) {
	class Add(x: Int): Operation(x)
	class Minus(x: Int): Operation(x)
	class Times(x: Int): Operation(x)
	object OtherOperation: Operation(0)
}