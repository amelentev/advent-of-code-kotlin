import kotlin.math.abs

fun main() {
    val input = readInput("Day15")
    val maxxy = 4000000L
    val coords = input.map { s ->
        s.split("Sensor at x=", ", y=", ": closest beacon is at x=").drop(1).map { it.toLong() }
    }
    val minx = coords.minOf { minOf(it[0], it[2]) }
    val maxx = coords.maxOf { maxOf(it[0], it[2]) }
    var res1 = 0
    fun check(x: Long, y: Long) = coords.all { (xs,ys,xb,yb) ->
        val d = abs(xs - xb) + abs(ys - yb)
        abs(x-xs) + abs(y-ys) > d
    }
    val y1 = maxxy / 2
    for (x in (2*minx - maxx) .. (2*maxx - minx)) {
        if (coords.any { (_,_,xb,yb) -> xb == x && yb == y1 }) continue
        if (check(x,y1)) continue
        res1++
    }
    println(res1)
    fun dist(c: List<Long>) = abs(c[0]-c[2]) + abs(c[1]-c[3])
    val res2 = HashSet<Long>()
    for (y in 0 .. maxxy) {
        for (c in coords) {
            val dx = dist(c) - abs(y - c[1])
            for (x in listOf(c[0] - dx - 1, c[0] + dx + 1)) {
                if (x in 0..maxxy && check(x, y)) {
                    res2.add(x * 4000000 + y)
                }
            }
        }
    }
    println(res2)
    for (x in 0 .. maxxy) {
        for (y in 0 .. maxxy) {
            if (check(x,y)) {
                println(x*4000000 + y)
            }
        }
    }
}
