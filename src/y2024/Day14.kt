package y2024

import readInput

fun main() {
    /*val n = 7
    val m = 11*/
    val n = 103
    val m = 101
    val robots = readInput("Day14").map {
        it.split("p=", ",", " v=").filter { it.isNotEmpty() }.map { it.toInt() }
    }
    fun move(robot: List<Int>, steps: Int): Pair<Int, Int> {
        val (x,y,vx,vy) = robot
        val x1 = (((x + steps * vx) % m) + m) % m
        val y1 = (((y + steps * vy) % n) + n) % n
        return x1 to y1
    }
    val steps = 100
    val res = LongArray(4)
    for (robot in robots) {
        val (x1,y1) = move(robot, steps)
        if (x1 == m/2 || y1 == n/2) continue
        val quadrant = (if (x1 < m/2) 0 else 1) * 2 + if (y1 < n/2) 0 else 1
        res[quadrant]++
    }
    println(res.joinToString(" "))
    println(res.reduce { a, b -> a*b })

    var maxAdj = 0
    for (step in 1..10000000) {
        val map = Array(n) { StringBuilder(" ".repeat(m)) }
        for (robot in robots) {
            val (x1,y1) = move(robot, step)
            map[y1][x1] = 'x'
        }
        var adj = 0
        for (i in 0 until n -1) {
            for (j in 0 until m-1) {
                if (map[i][j] == map[i][j+1]) adj++
                if (map[i][j] == map[i+1][j]) adj++
            }
        }
        if (adj > maxAdj) {
            maxAdj = adj
            println("------------- $step $adj")
            println(map.joinToString("\n"))
        }
    }
}
