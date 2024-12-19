package y2024

import readInput

fun main() {
    val input = readInput("Day19")
    val towels = input[0].split(", ").toHashSet()
    fun check(design: String): Long {
        val res = LongArray(design.length+1)
        res[0] = 1
        for (i in design.indices) {
            if (res[i] == 0L) continue
            for (j in i+1 .. design.length) {
                if (design.substring(i, j) in towels) {
                    res[j] += res[i]
                }
            }
        }
        return res[design.length]
    }
    val designs = input.drop(2)
    println(designs.count { check(it) > 0 })
    println(designs.sumOf { check(it) })
}
