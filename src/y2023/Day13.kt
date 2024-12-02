fun main() {
    fun solve(map: List<CharArray>): MutableList<Int> {
        val n = map.size
        val res = mutableListOf<Int>()
        for (i in 0 until n-1) {
            val ok = (0 .. i).all { i1 ->
                val i2 = i + 1 + (i-i1)
                i2 !in map.indices || map[i1].contentEquals(map[i2])
            }
            if (ok) res.add(i+1)
        }
        return res
    }
    fun transpose(map: List<CharArray>): List<CharArray> {
        return map[0].indices.map { j ->
            map.indices.joinToString("") { map[it][j].toString() }.toCharArray()
        }
    }
    fun solve1(map: List<CharArray>): List<Int> {
        val rows = solve(map)
        val cols = solve(transpose(map))
        return cols + rows.map { it*100 }
    }
    fun solve2(map: List<CharArray>): Int {
        val r1 = solve1(map).toSet()
        for (i in map.indices) {
            for (j in map[i].indices) {
                val old = map[i][j]
                map[i][j] = when (old) {
                    '.' -> '#'
                    '#' -> '.'
                    else -> error(old)
                }
                val r2 = solve1(map).toSet() - r1
                if (r2.isNotEmpty()) {
                    return r2.sum()
                }
                map[i][j] = old
            }
        }
        error("solve2")
    }
    val input = readInput("Day13")
    val curmap = mutableListOf<CharArray>()
    var res1 = 0
    var res2 = 0
    for (i in input.indices) {
        if (input[i].isEmpty()) {
            res1 += solve1(curmap).sum()
            res2 += solve2(curmap)
            curmap.clear()
        } else {
            curmap.add(input[i].toCharArray())
        }
    }
    res1 += solve1(curmap).sum()
    res2 += solve2(curmap)
    println(res1)
    println(res2)
}