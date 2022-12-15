fun main() {
    val input = readInput("Day14")
    val m = 1000
    val n = 1000
    val map = Array(n) { BooleanArray(m) }
    var maxy = 0
    for (s in input) {
        val trace = s.split(" -> ").map { p ->
            p.split(',').map { it.toInt() }
        }
        fun range(a: Int, b: Int) = if (a <= b) a..b else b..a
        for ((p0, p1) in trace.zipWithNext()) {
            for (x in range(p0[0], p1[0])) {
                for (y in range(p0[1], p1[1])) {
                    map[y][x] = true
                    maxy = maxOf(maxy, y)
                }
            }
        }
    }
    val floor = maxy + 2
    var res1 = 0
    var res2 = 0
    while (true) {
        var sandx = 500
        var sandy = 0
        if (map[sandy][sandx]) break
        while (sandy < floor - 1) {
            when {
                !map[sandy+1][sandx] -> sandy++
                !map[sandy+1][sandx-1] -> {
                    sandx--
                    sandy++
                }
                !map[sandy+1][sandx+1] -> {
                    sandx++
                    sandy++
                }
                else -> {
                    map[sandy][sandx] = true
                    break
                }
            }
        }
        if (sandy > maxy && res1 == 0) {
            res1 = res2
        }
        map[sandy][sandx] = true
        res2++
    }
    println(res1)
    println(res2)
}
