package y2025

import readInput

fun main() {
    val (ranges, ids) = readInput("Day05").let { lines ->
        val sep = lines.indexOf("")
        val ranges = lines.subList(0, sep).map { line ->
            line.split("-").map { it.toLong() }
        }.sortedWith(compareBy({ it[0] }, { it[1] }))
        ranges to lines.subList(sep + 1, lines.size).map { it.toLong() }
    }

    val res1 = ids.count { id ->
        ranges.any { (start, end) ->
            id in start .. end
        }
    }
    println(res1)

    var lastFresh = ranges.first().first()-1
    var res2 = 0L
    for ((start, end) in ranges) {
        res2 += maxOf(end - maxOf(start, lastFresh+1)+1, 0)
        lastFresh = maxOf(lastFresh, end)
    }
    println(res2)
}
