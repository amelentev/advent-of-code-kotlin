import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

fun main() {
    val input = readInput("Day10")
    val n = input.size
    val m = input[0].length
    val south = setOf('|', '7', 'F')
    val north = setOf('|', 'L', 'J')
    val west = setOf('-', 'J', '7')
    val east = setOf('-', 'L', 'F')
    fun solve1(i0: Int, j0: Int): Pair<Int, Int> {
        val len = Array(n) { IntArray(m) }
        len[i0][j0] = 1
        val queue = ArrayDeque(listOf(i0 to j0))
        val path = mutableListOf(i0 to j0)
        while (queue.isNotEmpty()) {
            val (i, j) = queue.removeFirst()
            val c = input[i][j]
            fun go(i1: Int, j1: Int, set: Set<Char>): Boolean {
                if (i1 !in input.indices || j1 !in input[i1].indices || input[i1][j1] !in set || len[i1][j1] > 0) return false
                queue.add(i1 to j1)
                path.add(i1 to j1)
                len[i1][j1] = len[i][j] + 1
                return true
            }
            when (c) {
                '|' -> {
                    go(i-1, j, south)
                    go(i+1, j, north)
                }
                '-' -> {
                    go(i, j-1, east)
                    go(i, j+1, west)
                }
                'L' -> {
                    go(i-1, j, south)
                    go(i, j+1, west)
                }
                'J' -> {
                    go(i-1, j, south)
                    go(i, j-1, east)
                }
                '7' -> {
                    go(i, j-1, east)
                    go(i+1, j, north)
                }
                'F' -> {
                    go(i, j+1, west)
                    go(i+1, j, north)
                }
                'S' -> {
                    go(i-1, j, south) ||
                    go(i+1, j, north) ||
                    go(i, j-1, east) ||
                    go(i, j+1, west)
                }
            }
        }
        val res1 = (path.size+1)/2
        var res2 = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (len[i][j] > 0) continue
                var ang = 0.0
                for (k in path.indices) {
                    val a = path[k]
                    val b = i to j
                    val c = path[(k+1) % path.size]
                    val a1 = atan2(a.first - b.first + 0.0, a.second - b.second + 0.0)
                    var a2 = atan2(c.first - b.first + 0.0, c.second - b.second + 0.0)
                    if (a2 - a1 > PI) a2 -= 2*PI
                    if (a2 - a1 < -PI) a2 += 2*PI
                    ang += a2 - a1
                }
                if (abs(ang) < 1E-9) continue
                res2 ++
            }
        }
        return res1 to res2
    }
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == 'S') {
                val (res1, res2) = solve1(i, j)
                println(res1)
                println(res2)
            }
        }
    }
}