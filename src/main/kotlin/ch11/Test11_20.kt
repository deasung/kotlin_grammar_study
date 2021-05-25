package ch11

import java.util.stream.IntStream
import kotlin.system.measureTimeMillis


//11-20 코드블록의 시간 측정하기
fun doubleIt(x: Int) : Int {
    Thread.sleep(100L)
    println("doubling $x with on thread ${Thread.currentThread().name}")

    return x * 2
}


fun main() {

    println("${Runtime.getRuntime().availableProcessors()} processors")
    var time = measureTimeMillis {
        IntStream.rangeClosed(1, 6)
            .map { doubleIt(it) }
            .sum()
    }

    println("Parallel stream took ${time}ms")

}
