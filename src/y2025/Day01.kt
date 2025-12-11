package y2025

import readInput

fun main() {
    var state = 50
    val N = 100
    var res = 0
    var res2 = 0
    val input = readInput("Day01")
    for (step in input) {
        val (dir, n) = step[0] to step.drop(1).toInt()
        val newState =
            if (dir == 'R') state + n
            else state - n
        res2 += if (newState > 0) {
            newState / 100
        } else if (state > 0) {
            1 - newState / 100
        } else {
            - newState / 100
        }

        state = ((newState % N) + N) % N
        if (state == 0) res ++
        println("$state, $res2")
    }
    println(res)
    println(res2)
}
