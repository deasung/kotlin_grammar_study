package ch11

import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextLong

//예제 11-25 여러 쓰레드 조인하기
//쓰레드 조인 -> 일반 쓰레드가 다른 쓰레드가 끝날때까지 대기하게 하기
fun main() {

    (0..5).forEach { n ->

        val sleepTime = Random.nextLong(range = 0..1000L)

        thread {
            Thread.sleep(sleepTime)
            println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
        }.join()

    }

}