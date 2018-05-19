class A public constructor() {
    
    init {
        f()
    }
    
    val a = "a"
    
    fun f() {
        println(a)
    }
}

fun main(args: Array<String>) {
    A()
}