package y2024

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("Day21")

    val numPad = listOf(
        "789",
        "456",
        "123",
        ".0A"
    )
    val dirPad = listOf(
        ".^A",
        "<v>"
    )
    fun find(map: List<String>, c: Char) = map.mapIndexedNotNull { i, line -> line.indexOf(c).takeIf { it != -1 }?.let { j -> i to j } }.single()
    fun getPaths(map: List<String>, cur: Char, next: Char, gapI: Int): Set<String> {
        val (i0, j0) = find(map, cur)
        val (i1, j1) = find(map, next)
        val di = i1 - i0
        val dj = j1 - j0
        val si = (if (di < 0) "^" else "v").repeat(abs(di))
        val sj = (if (dj < 0) "<" else ">").repeat(abs(dj))
        val options = hashSetOf<String>()
        if (!(i0 + di == gapI && j0 == 0)) {
            options.add(si + sj)
        }
        if (!(i0 == gapI && j0 + dj == 0)) {
            options.add(sj + si)
        }
        return options
    }
    val memo = Array(25) {
        HashMap<String, Long>()
    }
    fun solveDir(path: String, dirRobots: Int): Long {
        if (dirRobots == 0) return path.length.toLong()
        fun solve(): Long {
            var prev = 'A'
            var res = 0L
            for (c in path) {
                res += getPaths(dirPad, prev, c, 0).minOf {
                    solveDir(it+"A", dirRobots-1)
                }
                prev = c
            }
            return res
        }
        //return solve()
        return memo[dirRobots-1].computeIfAbsent(path) {
            solve()
        }
    }
    fun solveNum(code: String, dirRobots: Int): Long {
        var prev = 'A'
        var res = 0L
        for (c in code) {
            res += getPaths(numPad, prev, c, numPad.lastIndex).minOf {
                solveDir(it + "A", dirRobots)
            }
            prev = c
        }
        return res
    }
    for (dirRobots in listOf(2, 25)) {
        var res = 0L
        for (code in input) {
            val r = solveNum(code, dirRobots)
            res += r * code.removeSuffix("A").toLong()
        }
        println(res)
    }
}
