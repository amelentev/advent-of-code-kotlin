package y2025

import readInput

fun main() {
    val input = readInput("Day04").map { it.toCharArray() }
    val n = input.size
    val m = input[0].size
    fun isPaper(i: Int, j: Int) =
        (i in 0 until n && j in 0 until m && input[i][j] == '@')
    fun canMove(i: Int, j: Int): Boolean {
        if (!isPaper(i, j)) return false
        var papers = -1
        for (i1 in i-1..i+1) {
            for (j1 in j-1..j+1) {
                if (isPaper(i1, j1)) papers ++
            }
        }
        return papers < 4
    }
    var res1 = 0
    val queue = ArrayDeque<Pair<Int, Int>>()
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (canMove(i, j)) {
                res1 ++
                queue.add(i to j)
            }
        }
    }
    println(res1)

    var res2 = 0
    while (queue.isNotEmpty()) {
        val (i,j) = queue.removeFirst()
        for (i1 in i-1 .. i+1) {
            for (j1 in j-1 .. j+1) {
                if (canMove(i1, j1)) {
                    res2++
                    input[i1][j1] = '.'
                    queue.add(Pair(i1, j1))
                }
            }
        }
    }
    println(res2)
}
