import java.util.*

fun main() {
    val rocks = arrayOf(
        arrayOf(
            "####"
        ),
        arrayOf(
            ".#.",
            "###",
            ".#.",
        ),
        arrayOf(
            "..#",
            "..#",
            "###",
        ).reversed().toTypedArray(),
        arrayOf(
            "#",
            "#",
            "#",
            "#",
        ),
        arrayOf(
            "##",
            "##",
        )
    )
    val wind = readInput("Day17").single()
    fun solve(): Sequence<Int> {
        var time = 0
        var maxy = 0
        val m = 7
        val map = Array(10_000_000) { BooleanArray(m+2) }
        for (y in map.indices) {
            map[y][0] = true
            map[y][m+1] = true
            if (y == 0) Arrays.fill(map[y], true)
        }
        fun fits(x: Int, y: Int, rock: Array<String>): Boolean {
            for (y1 in rock.indices) {
                for (x1 in rock[y1].indices) {
                    val x2 = x + x1
                    val y2 = y + y1
                    if (rock[y1][x1] == '#' && map[y2][x2]) return false
                }
            }
            return true
        }
        fun place(x: Int, y: Int, rock: Array<String>) {
            for (y1 in rock.indices) {
                for (x1 in rock[y1].indices) {
                    val x2 = x + x1
                    val y2 = y + y1
                    if (rock[y1][x1] == '#') {
                        maxy = maxOf(maxy, y2)
                        map[y2][x2] = true
                    }
                }
            }
        }
        fun printMap(ySize: Int): String {
            val sb = StringBuilder()
            for (y in maxy downTo maxOf(0, maxy - ySize)) {
                sb.append(map[y].joinToString("") { if (it) "#" else "." }).append('\n')
            }
            return sb.toString()
        }
        fun dropRock(r: Int) {
            val rock = rocks[r % rocks.size]
            var x = 3
            var y = maxy + 4
            while (true) {
                val xw = when (wind[time % wind.length]) {
                    '>' -> x + 1
                    '<' -> x - 1
                    else -> error("")
                }
                time++
                if (fits(xw, y, rock)) {
                    x = xw
                }
                if (!fits(x, y-1, rock)) {
                    place(x, y, rock)
                    break
                } else {
                    y--
                }
            }
        }
        return sequence {
            var r = 0
            while (true) {
                dropRock(r++)
                yield(maxy)
            }
        }
    }
    println(solve().drop(2021).first())

    val heights = solve().take(1_000_000).toList()
    val startCycle = 100_000
    val cycLen = (10*rocks.size until heights.size).find { cycLen ->
        (0 until cycLen).all {
            (1..100).all { k ->
                heights[startCycle + it+1] - heights[startCycle + it] == heights[startCycle + k*cycLen + it+1] - heights[startCycle + k*cycLen + it]
            }
        }
    }!!
    println(cycLen)
    val cycHeight = heights[startCycle + cycLen] - heights[startCycle]
    var rounds = 1000000000000L - startCycle
    val t = rounds / cycLen
    var res2 = heights[startCycle] + t * cycHeight
    rounds -= t*cycLen
    res2 += heights[startCycle + rounds.toInt()] - heights[startCycle]
    println(res2-1)
}
