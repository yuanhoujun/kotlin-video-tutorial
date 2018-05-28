package com.youngfeng.kotlin

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-28 11:35
 */
class Tank: Moveable {
    private var proxy: Moveable? = null

    constructor(proxy: Moveable) {
        this.proxy = proxy
    }

    override fun move() {
        proxy?.move()
    }
}

class TankProxy: Moveable {

    override fun move() {
        println("Tank proxy moving...")
    }
}

class Tank1(proxy: Moveable): Moveable by proxy {

    override fun move() {
        println("Tank1 moving...")
    }
}

fun main(args: Array<String>) {
    val proxy = TankProxy()
    val tank = Tank1(proxy)
    tank.move()
}