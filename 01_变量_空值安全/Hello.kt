var str: String? = null
var str1: String = "Hello, world"
var isRight: Boolean? = true
var str2 = "abc"
var str3 = null // 类型Nothing

class A {
	lateinit var str4: String 
}

fun main(args: Array<String>) {
	println("Hello, world")
	println(str?.length) // if (str != null) str!!.length else null
	if (null != str) {	
		println(str!!.length)
	}
	
	if (null != isRight && isRight!!) {
		println("isRight = true")
	}

	 if (isRight ?: false) {
                println("isRight = true")
        }
	
	//	str2 = 1	
	println(str2)
	
	var s = "Scott Smith"
	println("Hello, $s")
	println("${opt()}gmail.com")
	
	val a = A()
	if (a::str4.isInitialized) {
		println("str4已经完成了初始化: ${a.str4}")
	} else {
		println("str4还没有完成初始化")
	}
}

fun opt(): String {
	return "@"
}
