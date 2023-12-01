import kotlin.math.abs

fun main() {
    val input = readInput("Day09")
    data class Point(val x: Int, val y: Int)
    operator fun Point.plus(p: Point) = Point(x + p.x, y + p.y)
    operator fun Point.minus(p: Point) = Point(x - p.x, y - p.y)
    val dirs = mapOf(
        'R' to Point(1, 0),
        'U' to Point(0, 1),
        'L' to Point(-1, 0),
        'D' to Point(0, -1),
    )
    val rope = Array(10) { Point(0, 0)}
    val res1 = mutableSetOf(rope[0])
    val res2 = mutableSetOf(rope[0])
    for (s in input) {
        val (dir, c) = s.split(' ')
        repeat(c.toInt()) {
            rope[0] += dirs[dir[0]]!!
            for (i in 1 until rope.size) {
                var d = rope[i-1] - rope[i]
                if (abs(d.x) > 1 || abs(d.y) > 1) {
                    if (abs(d.x) > 1) d = d.copy(x = d.x / abs(d.x))
                    if (abs(d.y) > 1) d = d.copy(y = d.y / abs(d.y))
                    rope[i] += d
                }
            }
            res1.add(rope[1])
            res2.add(rope[9])
        }
    }
    println(res1.size)
    println(res2.size)
}
