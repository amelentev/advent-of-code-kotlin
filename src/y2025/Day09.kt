package y2025

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("Day09").map { s ->
        s.split(",").map { it.toInt() }
    }
    val n = input.size
    run {
        var res1 = 0L
        for (i1 in input.indices) {
            for (i2 in i1+1 until input.size) {
                val (x1,y1) = input[i1]
                val (x2,y2) = input[i2]
                val mx = abs(x1-x2) + 1
                val my = abs(y1-y2) + 1
                res1 = maxOf(res1, mx*my.toLong())
            }
        }
        println(res1)
    }

    run {
        val xs = input.map { it[0] }.distinct().sorted()
        val ys = input.map { it[1] }.distinct().sorted()
        val xm = xs.withIndex().associate { (i, x) -> x to i }
        val ym = ys.withIndex().associate { (i, y) -> y to i }
        fun mapXY(p: List<Int>) = xm[p[0]]!! to ym[p[1]]!!
        val map = Array(ys.size) {
            CharArray(xs.size) { ' ' }
        }
        fun sign(x: Int) = if (x > 0) 1 else if (x < 0) -1 else 0
        for (i1 in input.indices) {
            var (x,y) = mapXY(input[i1])
            val (x2,y2) = mapXY(input[(i1 + 1) % n])
            val (dx,dy) = sign(x2-x) to sign(y2-y)
            map[y][x] = '#'
            while (!(x == x2 && y == y2)) {
                x += dx
                y += dy
                map[y][x] = 'X'
            }
            map[y2][x2] = '#'
        }
        fun fill(x0: Int, y0: Int) {
            val queue = ArrayDeque<Pair<Int, Int>>()
            fun add(x: Int, y: Int) {
                if (x !in xs.indices || y !in ys.indices) return
                if (map[y][x] != ' ') return
                map[y][x] = '.'
                queue.add(x to y)
            }
            add(x0, y0)
            while (queue.isNotEmpty()) {
                val (x, y) = queue.removeFirst()
                add(x + 1, y)
                add(x - 1, y)
                add(x, y + 1)
                add(x, y - 1)
            }
        }
        fun dump() {
            println("-".repeat(xs.size))
            for (s in map) {
                println(String(s))
            }
        }
        dump()
        for (x in 1 until xs.size) {
            if (map[1][x-1] != ' ' && map[1][x] == ' ') { // not always right
                fill(x, 1)
                break
            }
        }
        dump()

        var res2 = 0L
        for (i1 in input.indices) {
            i2@for (i2 in i1+1 until input.size) {
                val (x1,y1) = mapXY(input[i1])
                val (x2,y2) = mapXY(input[i2])
                for (x in minOf(x1, x2)..maxOf(x1, x2)) {
                    for (y in minOf(y1, y2)..maxOf(y1, y2)) {
                        if (map[y][x] == ' ') continue@i2
                    }
                }
                val mx = abs(xs[x1]-xs[x2]) + 1
                val my = abs(ys[y1]-ys[y2]) + 1
                res2 = maxOf(res2, mx*my.toLong())
            }
        }
        println(res2)
    }
}
