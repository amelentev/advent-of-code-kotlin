package y2024

import readInput

fun main() {
    val A = readInput("Day04").map { ".$it." }.let {
        val space = ".".repeat(it[0].length)
        listOf(space) + it + listOf(space)
    }

    run {
        val xmas = "XMAS"
        var res = 0
        for (di in -1..1) {
            for (dj in -1..1) {
                if (di == 0 && dj == 0) continue
                for (i in A.indices) {
                    for (j in A[i].indices) {
                        if (xmas.indices.all { A[i + di*it][j + dj*it] == xmas[it] }) {
                            res++
                        }
                    }
                }
            }
        }
        println(res)
    }
    run {
        var res = 0
        fun isMs(a: Char, b: Char) = a == 'M' && b == 'S' || a == 'S' && b == 'M'
        for (i in A.indices) {
            for (j in A[i].indices) {
                if (A[i][j] != 'A') continue
                if (isMs(A[i-1][j-1], A[i+1][j+1]) && isMs(A[i-1][j+1], A[i+1][j-1])) {
                    res++
                }
            }
        }
        println(res)
    }
}
