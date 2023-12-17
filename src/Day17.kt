fun main() {
    val input = readInput("Day17")
    val (n,m) = input.size to input[0].length
    val dirs = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0) // east south west north
    fun solve(minSteps: Int, maxSteps: Int): Int {
        val loss = Array(n) {
            Array(m) {
                IntArray(4) { -1 }
            }
        }
        val queue = ArrayDeque<Triple<Int,Int,Int>>()
        fun addQ(i: Int, j: Int, d1: Int, h: Int) {
            val d = d1 % 4
            if (i in input.indices && j in input[i].indices) {
                val l = loss[i][j][d]
                if (l < 0 || l > h) {
                    loss[i][j][d] = h
                    queue.add(Triple(i,j,d))
                }
            }
        }
        addQ(0,0,0,0)
        addQ(0,0,1,0)
        while (queue.isNotEmpty()) {
            val (i,j,d) = queue.removeFirst()
            var h = loss[i][j][d]
            for (k in 1 until minSteps) {
                val (i1,j1) = i + k*dirs[d].first to j + k*dirs[d].second
                if (i1 !in input.indices || j1 !in input[i1].indices) break
                h += (input[i1][j1] - '0')
            }
            for (k in minSteps .. maxSteps) {
                val (i1,j1) = i + k*dirs[d].first to j + k*dirs[d].second
                if (i1 !in input.indices || j1 !in input[i1].indices) break
                h += (input[i1][j1] - '0')
                addQ(i1, j1, d + 1, h)
                addQ(i1, j1, d + 3, h)
            }
        }
        return loss[n-1][m-1].filter { it >= 0}.min()
    }
    println(solve(1, 3))
    println(solve(4, 10))
}