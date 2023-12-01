fun main() {
    val map = readInput("Day24")
    val dirx = arrayOf(1, 0, -1, 0)
    val diry = arrayOf(0, 1, 0, -1)
    data class Wind(
        val x: Int,
        val y: Int,
        val dir: Int
    ) {
        fun next() = Wind(1 + (x - 1 + map[0].length-2 + dirx[dir]) % (map[0].length-2), 1 + (y - 1 + map.size-2 + diry[dir]) % (map.size-2), dir)
    }
    data class Pos(
        val x: Int,
        val y: Int
    ) {
        fun nexts() = listOf(Pos(x + 1, y), Pos(x, y+1), Pos(x-1, y), Pos(x, y-1), this)
    }
    val dirChars = arrayOf('>', 'v', '<', '^')
    var winds = HashSet<Wind>()
    map.forEachIndexed() { y, s ->
        s.forEachIndexed { x, ch ->
            val dir = dirChars.indexOf(ch)
            if (dir >= 0) winds.add(Wind(x, y, dir))
        }
    }
    fun solve(startPos: Pos, endPos: Pos): Int {
        var poss0 = HashSet<Pos>()
        poss0.add(startPos)
        var res = 0
        while (endPos !in poss0) {
            winds = winds.mapTo(HashSet()) { it.next() }
            val poss1 = HashSet<Pos>()
            for (p0 in poss0) {
                for (p1 in p0.nexts()) {
                    if (p1.y < 0 || p1.y >= map.size || map[p1.y][p1.x] == '#') continue
                    if (dirx.indices.any() { winds.contains(Wind(p1.x,p1.y,it)) }) continue
                    poss1.add(p1)
                }
            }
            poss0 = poss1
            res++
            print(".$res")
        }
        return res
    }
    val startPos = Pos(map[0].indexOf('.'), 0)
    val endPos = Pos(map.last().indexOf('.'), map.lastIndex)
    val res1 = solve(startPos, endPos)
    println("\n$res1")
    val res2 = res1 + solve(endPos, startPos) + solve(startPos, endPos)
    println("\n$res2")
}
