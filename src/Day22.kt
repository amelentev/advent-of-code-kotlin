fun main() {
    data class Point3(val x: Int, val y: Int, var z: Int)
    val input = readInput("Day22").map { brick ->
        brick.split('~').map { point ->
            val (x,y,z) = point.split(',').map { it.toInt() }
            Point3(x,y,z)
        }
    }.sortedBy { minOf(it[0].z, it[1].z) }
    fun getBrickIndex(p: Point3) = input.withIndex().find { (_, brick) ->
        val (p1,p2) = brick
        minOf(p1.x, p2.x) <= p.x && p.x <= maxOf(p1.x, p2.x) &&
                minOf(p1.y, p2.y) <= p.y && p.y <= maxOf(p1.y, p2.y) &&
                minOf(p1.z, p2.z) <= p.z && p.z <= maxOf(p1.z, p2.z)
    }?.index
    val ground = setOf(-1)
    fun getDependsOn(p1: Point3, p2: Point3): Set<Int> {
        val z = minOf(p1.z, p2.z)
        if (z == 1) return ground
        assert(z > 1)
        val res = HashSet<Int>()
        for (x in minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)) {
            for (y in minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)) {
                val sup = getBrickIndex(Point3(x,y,z-1))
                if (sup != null) res.add(sup)
            }
        }
        return res
    }
    for ((p1,p2) in input) {
        while (getDependsOn(p1,p2).isEmpty()) {
            p1.z --
            p2.z --
        }
    }
    val dependsOn = input.map { (p1, p2) ->
        getDependsOn(p1, p2)
    }
    val singleSupport = dependsOn.mapNotNull {
        it.singleOrNull()
    }.toSet()
    println(input.indices.count { it !in singleSupport })

    var res2 = 0
    for (i in input.indices) {
        val remove = mutableSetOf(i)
        do {
            var changed = false
            for (j in input.indices) {
                if (j !in remove && (dependsOn[j] - remove).isEmpty()) {
                    remove.add(j)
                    changed = true
                }
            }
        } while (changed)
        res2 += remove.size-1
    }
    println(res2)
}