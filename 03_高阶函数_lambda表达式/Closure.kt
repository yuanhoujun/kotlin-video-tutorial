
class A {}

fun doSomething() {
    var a = A()
    val action = { index: Int -> 
       // 这在Java语言中是不允许的
        a = A()
    }
}