package y2024

import readInput

fun main() {
    val input = readInput("Day03").joinToString("")

    fun solve(part2: Boolean): Long {
        var res = 0L
        var i = -1
        var enabled = true
        do {
            i = input.indexOf('(', i+1)
            if (i < 0) break
            fun isFunc(s: String) = input.substring(i-s.length, i) == s
            if (isFunc("mul") && enabled) {
                val i1 = input.indexOf(",", i)
                if (i1 < 0) continue
                val n1 = input.substring(i+1, i1)
                if (n1.length !in 1..3) continue
                val i2 = input.indexOf(")", i1)
                if (i2 < 0) continue
                val n2 = input.substring(i1+1, i2)
                if (n2.length !in 1..3) continue
                res += (n1.toLong() * n2.toLong())
            } else if (isFunc("don't") && input[i+1] == ')') {
                enabled = false || !part2
            } else if (isFunc("do") && input[i+1] == ')') {
                enabled = true
            }
        } while (true)
        return res
    }
    run {
        println(solve(false))
    }
    run {
        println(solve(true))
    }
}
