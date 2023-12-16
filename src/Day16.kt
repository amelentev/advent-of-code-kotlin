fun main() {
    val input = readInput("Day16")
    val dirs = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0) // east south west north
    fun solve(s0: Triple<Int,Int,Int>): Int {
        val visited = Array(input.size) {
            Array(input[it].length) {
                BooleanArray(4)
            }
        }
        val queue = ArrayDeque<Triple<Int, Int, Int>>()
        fun addQ(i: Int, j: Int, d: Int) {
            if (i in input.indices && j in input[i].indices && !visited[i][j][d]) {
                queue.add(Triple(i,j,d))
                visited[i][j][d] = true
            }
        }
        visited[s0.first][s0.second][s0.third] = true
        queue.add(s0)
        while (queue.isNotEmpty()) {
            val (i,j,d) = queue.removeFirst()
            fun go() {
                val (di, dj) = dirs[d]
                addQ(i+di, j+dj, d)
            }
            when (input[i][j]) {
                '.' -> go()
                '/' -> {
                    val en = listOf(0, 3)
                    val sw = listOf(1, 2)
                    val d1 = (en - d).singleOrNull() ?: (sw - d).single()
                    val (di, dj) = dirs[d1]
                    addQ(i+di, j+dj, d1)
                }
                '\\' -> {
                    val es = listOf(0, 1)
                    val wn = listOf(2, 3)
                    val d1 = (es - d).singleOrNull() ?: (wn - d).single()
                    val (di, dj) = dirs[d1]
                    addQ(i+di, j+dj, d1)
                }
                '|' -> {
                    if (d == 0 || d == 2) {
                        addQ(i-1, j, 3)
                        addQ(i+1, j, 1)
                    } else {
                        go()
                    }
                }
                '-' -> {
                    if (d == 1 || d == 3) {
                        addQ(i, j-1, 2)
                        addQ(i, j+1, 0)
                    } else {
                        go()
                    }
                }
            }
        }
        return visited.sumOf { vi -> vi.count { vj -> vj.any { it } } }
    }
    println(solve(Triple(0, 0, 0)))
    val edges = input.indices.flatMap { i ->
        listOf(Triple(i, 0, 0), Triple(i, input[i].lastIndex, 2))
    } + input[0].indices.flatMap { j ->
        listOf(Triple(0, j, 1), Triple(input.lastIndex, j, 3))
    }
    println(edges.maxOf { solve(it) })
}
