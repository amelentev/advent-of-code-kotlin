package y2024

import readInput

fun main() {
    val input = readInput("Day06").map { it.toCharArray() }
    val diri = arrayOf(-1, 0, 1, 0)
    val dirj = arrayOf(0, 1, 0, -1)
    val (i0, j0) = input.mapIndexedNotNull { i, it ->
        val j = it.indexOf('^')
        if (j >= 0) i to j else null
    }.single()
    fun printMap() = input.joinToString("\n") { String(it) }

    fun solve1(): Array<Array<BooleanArray>>? {
        var (i, j) = i0 to j0
        var dir = 0
        val visited = Array(diri.size) {
            Array(input.size) {
                BooleanArray(input[it].size)
            }
        }
        while (true) {
            if (visited[dir][i][j]) return null
            visited[dir][i][j] = true
            val i1 = i + diri[dir]
            val j1 = j + dirj[dir]
            if (!(0 <= i1 && i1 < input.size && 0 <= j1 && j1 < input[i].size)) {
                break
            }
            if (input[i1][j1] == '#') {
                dir = (dir+1) % diri.size
            } else {
                i = i1
                j = j1
            }
        }
        return visited
    }

    val visited = solve1()!!
    var res1 = 0L
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (visited.indices.any { k -> visited[k][i][j] })
                res1++
        }
    }
    println(res1)
    var res2 =0L
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == '.' && visited.indices.any { k -> visited[k][i][j] }) {
                input[i][j] = '#'
                if (solve1() == null) res2++
                input[i][j] = '.'
            }
        }
    }
    println(res2)
}
