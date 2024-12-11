package y2024

import readInput
import kotlin.system.exitProcess

fun main() {
    val input = readInput("Day11")[0].split(" ").map { it.toLong() }
    val limit = Long.MAX_VALUE / 2024
    fun blink(stones: List<Long>): List<Long> {
        return stones.flatMap { stone ->
            if (stone == 0L) return@flatMap listOf(1L)
            if (stone >= limit) exitProcess(1)
            val s = stone.toString()
            if (s.length % 2 == 0) {
                listOf(s.substring(0, s.length/2).toLong(), s.substring(s.length/2).toLong())
            } else {
                listOf(stone * 2024L)
            }
        }
    }
    fun blink2(stones: Map<Long, Long>): Map<Long, Long> {
        val res = HashMap<Long, Long>()
        for ((stone, cnt) in stones) {
            fun inc(stone: Long) {
                res[stone] = (res[stone] ?: 0L) + cnt
            }
            if (stone == 0L) {
                inc(1L)
                continue
            }
            if (stone >= limit) exitProcess(1)
            val s = stone.toString()
            if (s.length % 2 == 0) {
                inc(s.substring(0, s.length/2).toLong())
                inc(s.substring(s.length/2).toLong())
            } else {
                inc(stone * 2024L)
            }
        }
        return res
    }
    run {
        var stones = input
        repeat(25) {
            stones = blink(stones)
        }
        println(stones.size)
    }
    run {
        var stones = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(75) {
            stones = blink2(stones)
        }
        println(stones.values.sum())
    }
}
