fun main() {
    data class Point3(
        val x: Int,
        val y: Int,
        val z: Int,
    ) {
        fun sides() = listOf(
            Point3(x+1, y, z), Point3(x-1, y, z),
            Point3(x, y+1, z), Point3(x, y-1, z),
            Point3(x, y, z+1), Point3(x, y, z-1),
        )
    }
    val input = readInput("Day18").map { line ->
        line.split(",").map { it.toInt() }.let {
            Point3(it[0], it[1], it[2])
        }
    }.toSet()
    var res1 = 0
    for (p0 in input) {
        for (p1 in p0.sides()) {
            if (!input.contains(p1)) res1++
        }
    }
    println(res1)
    val queue = ArrayDeque(listOf(Point3(0,0,0)))
    val visited = HashSet<Point3>()
    var res2 = 0
    while (queue.isNotEmpty() && queue.size < 200_000) {
        val p1 = queue.removeFirst()
        for (p2 in p1.sides()) {
            if (visited.contains(p2)) continue
            if (input.contains(p2)) {
                res2 ++
                continue
            }
            visited.add(p2)
            queue.addLast(p2)
        }
    }
    println(res2)
}
