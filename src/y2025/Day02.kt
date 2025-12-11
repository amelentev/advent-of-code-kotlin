package y2025

import readInput

fun main() {
    val input = readInput("Day02")[0].split(",").map {
        it.split("-").map { it.toLong() }
    }
    fun check1(n: Long): Boolean {
        val s = n.toString()
        return s.length % 2 == 0 && s.substring(0, s.length / 2) == s.substring(s.length / 2)
    }
    fun check2(n: Long): Boolean {
        val s = n.toString()
        return (1 .. s.length/2).any { k ->
            if (s.length % k != 0)  return@any false
            for (j in k until s.length step k) {
                if (s.substring(j, j+k) != s.substring(0, k)) return@any false
            }
            return true
        }
    }
    var res1 = 0L
    var res2 = 0L
    for (range in input) {
        val (start, end) = range
        for (n in start .. end) {
            if (check1(n)) res1 += n
            if (check2(n)) res2 += n
        }
    }
    println(res1)
    println(res2)
}
