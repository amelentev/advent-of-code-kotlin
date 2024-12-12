package y2024

import readInput

fun main() {
    val input = readInput("Day12").let { line ->
        val m = line[0].length
        listOf(".".repeat(m+2)) + line.map { ".$it." } + listOf(".".repeat(m+2))
    }
    val (n, m) = input.size to input[0].length
    val di = listOf(1, 0, -1, 0)
    val dj = listOf(0, 1, 0, -1)
    run {
        val visited = Array(n) { BooleanArray(m) }
        fun calcAreaAndPerimeter(i0: Int, j0: Int): Triple<Int, Int, List<Pair<Int, Int>>> {
            val queue = ArrayDeque<Pair<Int, Int>>()
            val plots = mutableListOf<Pair<Int, Int>>()
            val plot = input[i0][j0]
            queue.add(i0 to j0)
            visited[i0][j0] = true
            var area = 0
            var perimeter = 0
            while (queue.isNotEmpty()) {
                val (i1, j1) = queue.removeFirst()
                plots.add(i1 to j1)
                area += 1
                perimeter += 4
                for (k in di.indices) {
                    val i2 = i1 + di[k]
                    val j2 = j1 + dj[k]
                    if (i2 !in 0 until n || j2 !in 0 until m) continue
                    if (input[i2][j2] == plot) {
                        perimeter --
                        if (!visited[i2][j2]) {
                            visited[i2][j2] = true
                            queue.add(i2 to j2)
                        }
                    }
                }
            }
            return Triple(area, perimeter, plots)
        }
        fun calcSides(plots: List<Pair<Int, Int>>): Int {
            val plot = plots[0].let { (i,j) -> input[i][j] }
            var resUp = 0
            var resDown = 0
            var resLeft = 0
            var resRight = 0
            for ((i, j) in plots) {
                if (input[i-1][j] != plot && (input[i][j-1] != plot || input[i-1][j-1] == plot)) {
                    resUp++
                }
                if (input[i+1][j] != plot && (input[i][j-1] != plot || input[i+1][j-1] == plot)) {
                    resDown++
                }
                if (input[i][j-1] != plot && (input[i-1][j] != plot || input[i-1][j-1] == plot)) {
                    resLeft++
                }
                if (input[i][j+1] != plot && (input[i-1][j] != plot || input[i-1][j+1] == plot)) {
                    resRight++
                }
            }
            return resUp + resDown + resLeft + resRight
        }

        var res1 = 0L
        var res2 = 0L
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == '.' || visited[i][j]) continue
                val (a,p, plots) = calcAreaAndPerimeter(i, j)
                val sides = calcSides(plots)
                //println("${input[i][j]} $a $p $sides")
                res1 += a.toLong() * p
                res2 += a.toLong() * sides
            }
        }
        println(res1)
        println(res2)
    }
}
