package ch11

import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextLong


//예제 11-23 데몬 스레드 시작
//https://widevery.tistory.com/32 데몬프로세스

fun main() {

    (0..5).forEach { n ->

        val sleepTime = Random.nextLong(range = 0..1000L)
        thread(isDaemon = true) {
            Thread.sleep(sleepTime)
            println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
        }

    }

}