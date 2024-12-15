package y2024

import readInput

fun main() {
    val file = "Day15"
    val dirs = mapOf(
        '<' to (0 to -1),
        '>' to (0 to 1),
        '^' to (-1 to 0),
        'v' to (1 to 0),
    )
    fun findRobot(map: List<StringBuilder>) = map.mapIndexedNotNull { i, line ->
        line.indexOf('@').takeIf { it != -1 }?.let { j ->
            i to j
        }
    }.single()
    fun calcRes(map: List<StringBuilder>, box: Char): Long {
        var res = 0L
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == box) {
                    res += i * 100 + j
                }
            }
        }
        return res
    }
    run {
        val (map, moves) = readInput(file).let { input ->
            val i = input.indexOf("")
            input.subList(0, i).map { StringBuilder(it) } to input.subList(i + 1, input.size).joinToString("")
        }
        val (rr0, rc0) = findRobot(map)
        fun move1(rr: Int, rc: Int, dir: Char): Pair<Int, Int> {
            val (dr, dc) = dirs[dir]!!
            val (r1,c1) = (rr+dr) to (rc+dc)
            var (r,c) = r1 to c1
            while (map[r][c] == 'O') {
                r += dr
                c += dc
            }
            if (map[r][c] == '.') {
                map[r][c] = map[r1][c1]
                map[r1][c1] = '@'
                map[rr][rc] = '.'
                return r1 to c1
            }
            return rr to rc
        }
        run {
            var r = (rr0 to rc0)
            for (move in moves) {
                r = move1(r.first, r.second, move)
            }
            println(calcRes(map, 'O'))
        }
    }
    run {
        val (map, moves) = readInput(file).let { input ->
            val i = input.indexOf("")
            val map = input.subList(0, i).map { line ->
                StringBuilder(line.toList().joinToString("") {
                    when (it) {
                        '#' -> "##"
                        'O' -> "[]"
                        '@' -> "@."
                        '.' -> ".."
                        else -> error(it)
                    }
                })
            }
            map to input.subList(i + 1, input.size).joinToString("")
        }
        val (rr0, rc0) = findRobot(map)
        fun isBox(c: Char) = c == '[' || c == ']'
        fun moveH(rr: Int, rc: Int, dc: Int): Pair<Int, Int> {
            val c1 = rc+dc
            var c = c1
            while (isBox(map[rr][c])) {
                c += 2*dc
            }
            if (map[rr][c] == '.') {
                while (c != rc) {
                    map[rr][c] = map[rr][c-dc]
                    c -= dc
                }
                map[rr][rc] = '.'
                return rr to c1
            }
            return rr to rc
        }
        fun canMoveBoxV(r: Int, c: Int, dr: Int): Boolean {
            val c1 = when (val m = map[r][c]) {
                '.' -> return true
                '#' -> return false
                '[' -> c+1
                ']' -> c-1
                else -> error(m)
            }
            return canMoveBoxV(r+dr, c1, dr) && canMoveBoxV(r+dr, c, dr)
        }
        fun moveBoxV(r: Int, c: Int, dr: Int) {
            val c1 = when (val m = map[r][c]) {
                '.' -> return
                '[' -> c+1
                ']' -> c-1
                else -> error(m)
            }
            moveBoxV(r+dr, c, dr)
            moveBoxV(r+dr, c1, dr)
            map[r+dr][c] = map[r][c]
            map[r+dr][c1] = map[r][c1]
            map[r][c] = '.'
            map[r][c1] = '.'
        }
        fun moveV(rr: Int, rc: Int, dr: Int): Pair<Int, Int> {
            val r1 = rr+dr
            if (map[r1][rc] == '.') {
                map[r1][rc] = '@'
                map[rr][rc] = '.'
                return r1 to rc
            }
            if (isBox(map[r1][rc]) && canMoveBoxV(r1, rc, dr)) {
                moveBoxV(r1, rc, dr)
                map[r1][rc] = '@'
                map[rr][rc] = '.'
                return r1 to rc
            }
            return rr to rc
        }
        fun move2(rr: Int, rc: Int, dir: Char): Pair<Int, Int> {
            val (dr, dc) = dirs[dir]!!
            return if (dr == 0) return moveH(rr, rc, dc)
            else if (dc == 0) moveV(rr, rc, dr)
            else error(dir)
        }
        run {
            var r = (rr0 to rc0)
            for (move in moves) {
                r = move2(r.first, r.second, move)
            }
            println(calcRes(map, '['))
        }
    }
}
