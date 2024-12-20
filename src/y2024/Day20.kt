package y2024

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("Day20")
    fun find(c: Char) = input.mapIndexedNotNull { y, line ->
        line.indexOf(c).takeIf { it != -1 }?.let { x ->
            x to y
        }
    }.single()
    val end = find('E')
    //val start = find('S')
    val dist = Array(input.size) {
        IntArray(input.size) { Int.MAX_VALUE / 2 }
    }
    val queue = ArrayDeque<Pair<Int, Int>>()
    queue.add(end)
    dist[end.second][end.first] = 0
    val dirs = listOf(
        0 to 1, 1 to 0, 0 to -1, -1 to 0
    )
    while (queue.isNotEmpty()) {
        val (x, y) = queue.removeFirst()
        val d = dist[y][x]
        for (dir in dirs) {
            val (x1, y1) = x + dir.first to y + dir.second
            if (y1 in input.indices && x1 in input[y1].indices && input[y1][x1] != '#' && dist[y1][x1] > d+1) {
                queue.add(x1 to y1)
                dist[y1][x1] = d+1
            }
        }
    }

    fun solve(ct: Int, cd: Int): Int {
        var res = 0
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (dist[y][x] >= Int.MAX_VALUE/2) continue
                for (y1 in y-ct .. y+ct) {
                    for (x1 in x-ct..x+ct) {
                        val cheatTime = abs(x1-x) + abs(y1-y)
                        if (cheatTime > ct || y1 !in input.indices || x1 !in input[y1].indices) continue
                        val diff = dist[y][x] - dist[y1][x1] - cheatTime
                        if (diff >= cd) {
                            //println("$diff $x $y $x1 $y1")
                            res++
                        }
                    }
                }
            }
        }
        return res
    }

    println(solve(2, 100))
    println(solve(20, 100))
}
