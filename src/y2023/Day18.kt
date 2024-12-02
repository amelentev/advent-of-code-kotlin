fun main() {
    data class Point(val x: Long, val y: Long)
    fun solve(dirs: List<Pair<Char, Long>>): Long {
        var perimeter = 0L
        val points = run {
            val res = ArrayDeque<Point>()
            res.add(Point(0,0))
            for (dir in dirs) {
                val cur = res.last()
                val (d, l) = dir
                perimeter += l
                when (d) {
                    'R', '0' -> res.add(cur.copy(x = cur.x + l))
                    'D', '1' -> res.add(cur.copy(y = cur.y + l))
                    'L', '2' -> res.add(cur.copy(x = cur.x - l))
                    'U', '3' -> res.add(cur.copy(y = cur.y - l))
                    else -> error(d)
                }
            }
            res.dropLast(1)
            res
        }
        val n = points.size
        val res = points.indices.sumOf { i ->
            points[i].x * points[(i+1)%n].y - points[(i+1)%n].x * points[i].y
        }
        return res/2 + perimeter/2 + 1
    }
    val input = readInput("Day18")
    println(solve(input.map { line ->
        line.split(" ").let { it[0][0] to it[1].toLong() }
    }))
    println(solve(input.map { line ->
        val code = line.substringAfter('#').substringBefore(')')
        val l = code.substring(0, 5).toLong(16)
        code[5] to l
    }))
}
