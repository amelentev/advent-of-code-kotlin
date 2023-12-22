import kotlin.math.abs

fun main() {
    data class Point(val r: Int, val c: Int)
    operator fun Point.plus(p: Point) = Point(r + p.r, c + p.c)

    val dirs = listOf(Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0))
    fun solve1(input: List<String>, steps: Int): Int {
        val start = input.indexOfFirst { it.contains('S') }.let { i ->
            i to input[i].indexOf('S')
        }
        var prev = HashSet<Pair<Int, Int>>()
        prev.add(start)
        for (step in 1..steps) {
            val next = HashSet<Pair<Int, Int>>()
            for (s in prev) {
                for (d in dirs) {
                    val s1 = s.first + d.r to s.second + d.c
                    if (s1.first in input.indices && s1.second in input[s1.first].indices && input[s1.first][s1.second] != '#') {
                        next.add(s1)
                    }
                }
            }
            prev = next
        }
        return prev.size
    }

    fun Int.pmod(m: Int) = (this % m).let { if (it < 0) it + m else it }
    fun solve2(input: List<String>, steps: Int): Long {
        val (n,m) = input.size to input[0].length
        assert(n==m)

        val start = input.indexOfFirst { it.contains('S') }.let { i ->
            Point(i, input[i].indexOf('S'))
        }
        val D = HashMap<Point, Int>()
        val queue = ArrayDeque(listOf(start))
        D[start] = 0
        while (queue.isNotEmpty()) {
            val s = queue.removeFirst()
            val d = D[s]!!
            for (dir in dirs) {
                val s1 = s + dir
                if (input[s1.r.pmod(n)][s1.c.pmod(m)] == '#') continue
                if (s1 in D) continue
                if (abs(s1.r / n) > 4 || abs(s1.c / m) > 4) continue
                queue.add(s1)
                D[s1] = d + 1
            }
        }
        val memo = HashMap<Pair<Int, Boolean>, Long>()
        fun calc(d: Int, corner: Boolean): Long {
            return memo.getOrPut(d to corner) {
                val k = (steps - d) / n
                var res = 0L
                for (x in 1..k) {
                    val dd = d + x*n
                    if (dd <= steps && (dd%2 == steps%2)) {
                        res += if (corner) x + 1 else 1
                    }
                }
                res
            }
        }
        var res = 0L
        for (i in -(4*n-1)..<4*n) {
            for (j in -(4*m-1)..<4*m) {
                val d = D[Point(i,j)] ?: continue
                if (d <= steps && (d%2 == steps%2)) {
                    res ++
                }
                val ei = abs(i / n) == 3
                val ej = abs(j / m) == 3
                if (ei && ej) {
                    res += calc(d, true)
                } else if (ei || ej) {
                    res += calc(d, false)
                }
            }
        }
        return res
    }
    val test = readInput("Day21t")
    assert(solve1(test, 6) == 16)

    fun check2(steps: Int, ans: Long) {
        val res = solve2(test, steps)
        println("$steps:\t$res")
        assert(res == ans)
    }
    check2(6, 16)
    check2(50, 1594)
    check2(100, 6536)
    check2(500, 167004)
    check2(1000, 668697)
    check2(5000, 16733044)

    val input = readInput("Day21")
    println(solve1(input, 64))
    println(solve2(input, 26501365))
}
