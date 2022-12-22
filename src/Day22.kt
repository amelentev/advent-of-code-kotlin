private enum class Dir {
    RIGHT, DOWN, LEFT, UP
}
fun main() {
    val input = readInput("Day22")
    var map = input.takeWhile { it != "" }
    val maxx = map.maxOf { it.length }
    map = map.map { it.padEnd(maxx, ' ') }
    val path = input.dropWhile { it != "" }.drop(1).single().let { s ->
        var i0 = 0
        val res = mutableListOf<Int>()
        while (i0 < s.length) {
            val i1 = s.indexOfAny(charArrayOf('L', 'R'), i0)
            if (i1 > i0) {
                res.add(s.substring(i0, i1).toInt())
                res.add(if (s[i1] == 'L') -1 else +1)
                i0 = i1+1
            } else {
                res.add(s.substring(i0).toInt())
                break
            }
        }
        res.toList()
    }
    val dirs = arrayOf(
        intArrayOf(1, 0),
        intArrayOf(0, 1),
        intArrayOf(-1, 0),
        intArrayOf(0, -1),
    )
    fun rotate(dir: Dir, rot: Int) = when {
        rot < 0 -> Dir.values()[(dir.ordinal - 1 + dirs.size) % dirs.size]
        else -> Dir.values()[(dir.ordinal + 1) % dirs.size]
    }
    run {
        var x = map[0].indexOfFirst {it == '.' }
        var y = 0
        var dir = Dir.RIGHT
        for (i in path.indices step 2) {
            repeat(path[i]) {
                var x1 = x
                var y1 = y
                while (true) {
                    x1 = (x1 + dirs[dir.ordinal][0] + maxx) % maxx
                    y1 = (y1 + dirs[dir.ordinal][1] + map.size) % map.size
                    if (map[y1][x1] != ' ') break
                }
                if (map[y1][x1] == '#') return@repeat
                x = x1
                y = y1
            }
            if (i+1 < path.size) {
                dir = rotate(dir, path[i+1])
            }
        }
        println((y+1)*1000 + 4 * (x+1) + dir.ordinal)
    }
    run {
        val side = 50
        fun calcNext(x0: Int, y0: Int, dir: Dir): Triple<Int,Int,Dir> {
            val x1 = x0 + dirs[dir.ordinal][0]
            val y1 = y0 + dirs[dir.ordinal][1]
            return when {
                dir == Dir.LEFT && x1 == side-1 && y1 in 0 until side -> Triple(0, 3*side - (y1 % side) - 1, Dir.RIGHT)
                dir == Dir.UP && y1 == -1 && x1 in side until 2*side -> Triple(0, 3*side + (x1 % side), Dir.RIGHT)
                dir == Dir.RIGHT && x1 == 3*side -> Triple(2*side-1, 3*side - (y1 % side) - 1, Dir.LEFT)
                dir == Dir.DOWN && y1 == side && x1 in 2*side until 3*side -> Triple(2*side - 1, side + (x1 % side), Dir.LEFT)
                dir == Dir.UP && y1 == -1 && x1 in 2*side until 3*side -> Triple(x1 % side, 4*side-1, Dir.UP)

                dir == Dir.RIGHT && x1 == 2*side && y1 in side until 2*side -> Triple(2*side + (y1 % side), side-1, Dir.UP)
                dir == Dir.LEFT && x1 == side-1 && y1 in side until 2*side -> Triple((y1 % side), 2*side, Dir.DOWN)

                dir == Dir.LEFT && x1 == -1 && y1 in 2*side until 3*side -> Triple(side, side - (y1 % side) - 1, Dir.RIGHT)
                dir == Dir.UP && y1 == 99 && x1 in 0 until side -> Triple(side, side + (x1 % side), Dir.RIGHT)
                dir == Dir.RIGHT && x1 == 2*side && y1 in 2*side until 3*side -> Triple(3*side-1, side - (y1 % side) - 1, Dir.LEFT)
                dir == Dir.DOWN && y1 == 3*side && x1 in side until 2*side -> Triple(side-1, 3*side + (x1 % side), Dir.LEFT)

                dir == Dir.RIGHT && x1 == side && y1 in 3*side until 4*side -> Triple(side + (y1 % side), 3*side-1, Dir.UP)
                dir == Dir.DOWN && y1 == 4*side -> Triple(2*side + (x1 % side), 0, Dir.DOWN)
                dir == Dir.LEFT && x1 == -1 && y1 in 3*side until 4*side -> Triple(side + (y1 % side), 0, Dir.DOWN)

                else -> Triple(x1, y1, dir)
            }
        }
        var x = map[0].indexOfFirst {it == '.' }
        var y = 0
        var dir = Dir.RIGHT
        for (i in path.indices step 2) {
            repeat(path[i]) {
                val (x1,y1,dir1) = calcNext(x, y, dir)
                if (map[y1][x1] == '#') return@repeat
                x = x1
                y = y1
                dir = dir1
            }
            if (i+1 < path.size) {
                dir = rotate(dir, path[i+1])
            }
        }
        println((y+1)*1000 + 4 * (x+1) + dir.ordinal)
    }
}
