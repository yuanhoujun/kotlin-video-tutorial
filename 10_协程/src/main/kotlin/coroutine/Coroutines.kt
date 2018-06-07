package coroutine

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-07 16:03
 */

fun main(args: Array<String>) {
//    runBlocking {
//        val ctx = newSingleThreadContext("CTX")
//
//        val job1 = launch(CommonPool) {
//            while (true) {
//                delay(100)
//                log("Coroutine #1")
//            }
//        }
//
//        val job2 = launch(CommonPool) {
//            while (true) {
//                log("Coroutine #2")
//            }
//        }
//
//        job1.join()
//        job2.join()
//    }
//
//    println("Coroutine outside")

//    runBlocking {
//        val jobs = List(100_000) { // launch a lot of coroutines and list their jobs
//            launch {
//                delay(1000L)
//                log(".")
//            }
//        }
//        jobs.forEach { it.join() } // wait for all job
//    }


//    val jobs = List(100_000) { // launch a lot of coroutines and list their jobs
//        thread {
//            sleep(1000L)
//            print(".")
//        }
//    }
//    jobs.forEach { it.join() } // wait for all job

//    runBlocking {
//        val d1 = async(start = CoroutineStart.LAZY) {
//            println("async #1")
//            delay(1000)
//            3
//        }
//
//        val d2 = async(start = CoroutineStart.LAZY) {
//            println("async #2")
//            delay(2000)
//            5
//        }

//        println("${d1.await() + d2.await()}")



//    }


//    runBlocking {
//        val channel = Channel<Int>()
//
//        val job1 = launch {
//            for (i in 1..5) {
//                delay(1000)
//                channel.send(i * i)
//            }
//        }
//
//        val job2 = launch {
//            repeat(5) {
//                println("${channel.receive()}")
//            }
//        }
//
//        job1.join()
//        job2.join()
//    }

//    runBlocking {
//        val mutex = Mutex()
//
//        val ctx = newSingleThreadContext("CTX")
//        massiveRun(CommonPool) {
//            mutex.withLock {
//                counter ++
//            }
//        }
//        println("Counter = $counter")
//    }

    runBlocking {
        val counter = counterActor() // create the actor
        massiveRun(CommonPool) {
            counter.send(IncCounter)
        }
        // send a message to get a counter value from an actor
        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter = ${response.await()}")
        counter.close()
    }
}


suspend fun massiveRun(context: CoroutineContext, action: suspend () -> Unit) {
    val n = 1000
    val k = 1000
    val time = measureTimeMillis {
        val jobs = List(n) {
            launch(context) {
                repeat(k) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${n * k} actions in $time ms")
}

//@Volatile
//var counter = 0


sealed class CounterMsg
object IncCounter : CounterMsg() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

fun counterActor() = actor<CounterMsg> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")