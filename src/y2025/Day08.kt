package y2025

import readInput
import kotlin.math.sqrt

fun main() {
    val input = readInput("Day08")
        .map {
            it.split(",").map { it.toDouble() }
        }
    val maxK = 1000
    val n = input.size
    val circuits = IntArray(n) { it+1 }
    fun sqr(x: Double) = x * x
    fun dist(a: List<Double>, b: List<Double>) = sqrt(sqr(a[0] - b[0]) + sqr(a[1] - b[1]) + sqr(a[2]-b[2]))
    val closest = input.indices.flatMap { i ->
        input.indices.mapNotNull { j ->
            if (i >= j) return@mapNotNull null
            i to j
        }
    }.sortedBy { (i, j) -> dist(input[i], input[j]) }
    fun connect(k: Int) {
        val (p1, p2) = closest[k]
        val (c1, c2) = circuits[p1] to circuits[p2]
        if (c1 == c2) return
        for (i in circuits.indices) {
            if (circuits[i] == c2) circuits[i] = c1
        }
    }
    for (k in 0 until maxK) {
        connect(k)
    }
    val circuitSize = circuits.groupBy { it }.mapValues { it.value.size }
    val top3 = circuitSize.entries.sortedBy { -it.value }.take(3).map { it.value }
    val res1 = top3[0] * top3[1] * top3[2]
    println(res1)

    for (k in maxK until closest.size) {
        connect(k)
        if (circuits.distinct().size == 1) {
            val (p1, p2) = closest[k]
            val res2 = input[p1][0] * input[p2][0]
            println(res2.toLong())
            break
        }
    }
}
