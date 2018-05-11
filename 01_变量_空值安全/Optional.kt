var str = "Hello, world"
var str1: String? = null
var isRight: Boolean? = null

fun main(args: Array<String>) {
	println(str)
	if (null != str1) {
		println(str1!!.length)
	}
	
	if (isRight ?: false) {
		println("isRight = true")
	} else {
		println("isRight = null or false")
	}

}
