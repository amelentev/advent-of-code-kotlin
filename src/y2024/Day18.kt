package y2024

import readInput

fun main() {
    val n = 71
    val after = 1024
    val bytes = readInput("Day18").map {
        it.split(",").map { it.toInt() }.let { it[1] to it[0] }
    }

    fun solve1(bytes: HashSet<Pair<Int, Int>>): Int {
        fun printMap() {
            val res = StringBuilder()
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (i to j in bytes) res.append('#')
                    else res.append('.')
                }
                res.append('\n')
            }
            println(res.toString())
        }
        val res = Array(n) {
            IntArray(n) {
                Int.MAX_VALUE/2
            }
        }
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(0, 0))
        res[0][0] = 0
        val dirs = listOf(
            0 to 1, 1 to 0, 0 to -1, -1 to 0
        )
        while (queue.isNotEmpty()) {
            val (r,c) = queue.removeFirst()
            for ((dr,dc) in dirs) {
                val next = r+dr to c + dc
                val (r1,c1) = next
                if (r1 in 0 until n && c1 in 0 until n && res[r1][c1] > res[r][c] + 1 && next !in bytes) {
                    queue.add(next)
                    res[r1][c1] = res[r][c] + 1
                }
            }
        }
        //printMap()
        return res[n-1][n-1]
    }
    println(solve1(bytes.take(after).toHashSet()))
    for (res2 in after .. bytes.size) {
        if (solve1(bytes.take(res2).toHashSet()) >= Int.MAX_VALUE/2) {
            bytes[res2-1].let { (r, c) ->
                println("$c,$r")
            }
            break
        }
    }
}
