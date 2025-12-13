package y2025

import readInput

fun main() {
    val input = readInput("Day07")
    val m = input[0].length
    require(input.all { it.length == m })

    var res1 = 0
    var timelines = LongArray(m)
    timelines[input[0].indexOf('S')] = 1
    for (i in 1 until input.size) {
        val timelines1 = LongArray(m)
        for (j in 0 until m) {
            if (timelines[j] == 0L) continue
            if (input[i][j] == '^') {
                res1++
                if (j > 0) timelines1[j-1] += timelines[j]
                if (j+1 < m) timelines1[j+1] += timelines[j]
            } else {
                timelines1[j] += timelines[j]
            }
        }
        timelines = timelines1
    }
    println(res1)
    println(timelines.sum())
}
