fun main() {
    val input = readInput("Day12").map { it.toCharArray() }
    val n = input.size
    val m = input[0].size
    var source: Pair<Int, Int>? = null
    var end: Pair<Int, Int>? = null
    for (i in 0 until n) {
        for (j in 0 until m) {
            when (input[i][j]) {
                'S' -> {
                    source = i to j
                    input[i][j] = 'a'
                }
                'E' -> {
                    end = i to j
                    input[i][j] = 'z'
                }
            }
        }
    }
    require(end != null)
    require(source != null)

    val dist = Array(n) { IntArray(m) { Int.MAX_VALUE } }
    val queue = ArrayDeque<Pair<Int,Int>>()
    queue.add(end.first to end.second)
    dist[end.first][end.second] = 0

    while (queue.isNotEmpty()) {
        val (i0, j0) = queue.removeFirst()
        fun check(i: Int, j: Int) {
            if (i in 0 until n && j in 0 until m && input[i0][j0] - input[i][j] <= 1 && dist[i][j] > dist[i0][j0] + 1) {
                queue.add(i to j)
                dist[i][j] = dist[i0][j0] + 1
            }
        }
        check(i0, j0+1)
        check(i0+1, j0)
        check(i0, j0-1)
        check(i0-1, j0)
    }
    println(dist[source.first][source.second])

    var res2 = Int.MAX_VALUE
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (input[i][j] == 'a') {
                res2 = minOf(res2, dist[i][j])
            }
        }
    }
    println(res2)
}
