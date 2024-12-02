package y2024

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("Day01").map {
        it.split(Regex("\\W+")).map { it.toLong() }
    }
    val a1 = input.map { it[0] }.sorted()
    val a2 = input.map { it[1] }.sorted()
    var res = 0L
    for (i in a1.indices) {
        res += abs(a1[i] - a2[i])
    }
    println(res)

    var res2 = 0L
    val right = input.map { it[1]}.groupingBy { it }.eachCount().withDefault { 0 }
    for (a in input.map { it[0] }) {
        res2 += a * right.getValue(a)
    }
    println(res2)
}
