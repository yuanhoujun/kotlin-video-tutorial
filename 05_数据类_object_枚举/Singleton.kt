

class Car {}

object Factory {

	@JvmStatic
	fun create(): Car {
		return Car()
	}
}


val car = Factory.create()