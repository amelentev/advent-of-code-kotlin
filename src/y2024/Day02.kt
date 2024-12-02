package y2024

import readInput
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val input = readInput("Day02").map {
        it.split(Regex("\\W+")).map { it.toLong() }
    }
    fun isSafe(line: List<Long>): Boolean {
        val diffs = line.zipWithNext().map { it.first - it.second }
        return diffs.all { abs(it) in 1..3 } && diffs.all { it.sign == diffs[0].sign }
    }
    val res1 = input.count(::isSafe)
    println("Part 1: $res1")

    val res2 = input.count { line ->
        isSafe(line) || line.indices.any { i ->
            val lst = line.toMutableList().also {
                it.removeAt(i)
            }
            isSafe(lst)
        }
    }
    println("Part 2: $res2")
}
