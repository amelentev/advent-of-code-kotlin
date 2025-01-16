package y2024

import readInput

fun main() {
    val input = readInput("Day25")
    val n = 6
    val m = input[0].length
    val locks = mutableListOf<IntArray>()
    val keys = mutableListOf<IntArray>()
    for (i in input.indices step n+2) {
        val key = IntArray(m) { -1 }
        for (j in i .. i+n) {
            for (k in 0 until m) {
                if (input[j][k] == '#') key[k]++
            }
        }
        if (input[i].all { it == '#' }) locks.add(key)
        else keys.add(key)
    }
    fun match(lock: IntArray, key: IntArray) = (0 until m).all { lock[it] + key[it] <= n-1 }
    var res = 0
    for (lock in locks) {
        for (key in keys) {
            if (match(lock, key)) {
                res++
            }
        }
    }
    println(res)
}
