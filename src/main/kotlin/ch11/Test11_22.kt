package ch11

import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextLong

//예제 11-22 다수의 스레드를 임의의 간격으로 시작
fun main() {

    (0..5).forEach { n ->

        val sleepTime = Random.nextLong(range = 0..1000L)

        thread {

            Thread.sleep(sleepTime)
            println("${Thread.currentThread().name} for $n after ${sleepTime}ms")

        }

    }

}