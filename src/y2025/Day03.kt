package y2025

import readInput

fun main() {
    val input = readInput("Day03")
    fun solve1(s: String): Int {
        var best = 11
        for (i in s.indices) {
            for (j in i + 1 until s.length) {
                val a = (s[i] - '0')*10 + (s[j]-'0')
                best = maxOf(best, a)
            }
        }
        return best
    }
    fun solve2(s: String, n: Int): Long {
        var dp = LongArray(n+1) { 0 }
        for (i in s.indices) {
            val dp1 = LongArray(n+1) { 0 }
            for (k in 1 .. n) {
                dp1[k] = maxOf(dp[k], dp[k-1] * 10 + (s[i] - '0'))
            }
            dp = dp1
        }
        return dp[n]
    }
    val res1 = input.sumOf { solve1(it) }
    val res2 = input.sumOf { solve2(it, 12) }
    println(res1)
    println(res2)
}
