package y2024

import readInput

fun main() {
    val input = readInput("Day10")
    val (n, m) = input.size to input[0].length
    val di = listOf(1, 0, -1, 0)
    val dj = listOf(0, 1, 0, -1)
    fun calc1(i: Int, j: Int): Int {
        if (input[i][j] != '0') return 0
        val visited = Array(n) {
            BooleanArray(m)
        }
        visited[i][j] = true
        val queue = ArrayDeque(listOf(i to j))
        var res = 0
        while (queue.isNotEmpty()) {
            val (i1, j1) = queue.removeFirst()
            if (input[i1][j1] == '9') {
                res++
                continue
            }
            for (k in di.indices) {
                val (i2, j2) = i1 + di[k] to j1 + dj[k]
                if (i2 in 0 until n && j2 in 0 until m && input[i2][j2] == input[i1][j1]+1 && !visited[i2][j2]) {
                    visited[i2][j2] = true
                    queue.add(i2 to j2)
                }
            }
        }
        return res
    }
    fun calc2(i: Int, j: Int): Long {
        if (input[i][j] != '0') return 0
        val visited = Array(n) {
            LongArray(m)
        }
        visited[i][j] = 1
        val queue = ArrayDeque(listOf(i to j))
        var res = 0L
        while (queue.isNotEmpty()) {
            val (i1, j1) = queue.removeFirst()
            if (input[i1][j1] == '9') {
                res += visited[i1][j1]
                continue
            }
            for (k in di.indices) {
                val (i2, j2) = i1 + di[k] to j1 + dj[k]
                if (i2 in 0 until n && j2 in 0 until m && input[i2][j2] == input[i1][j1]+1) {
                    if (visited[i2][j2] == 0L) queue.add(i2 to j2)
                    visited[i2][j2] += visited[i1][j1]
                }
            }
        }
        return res
    }
    var res1 = 0
    var res2 = 0L
    for (i in input.indices) {
        for (j in input[i].indices) {
            val r1 = calc1(i,j)
            val r2 = calc2(i,j)
            res1 += r1
            res2 += r2
        }
    }
    println(res1)
    println(res2)
}
