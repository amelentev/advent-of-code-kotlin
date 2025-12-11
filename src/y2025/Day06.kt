package y2025

import readInput

fun main() {
    val input = readInput("Day06")
    require(input.all { it.length == input[0].length })
    run {
        val input1 = input.map { line ->
            line.split(" ").filter { it.isNotEmpty() }
        }
        require(input1.all { it.size == input1[0].size })
        val m = input1[0].size
        var res1 = 0L
        for (j in 0 until m) {
            val op = input1.last()[j][0]
            var res = input1.first()[j].toLong()
            for (i in 1 until input1.size-1) {
                if (op == '+') res += input1[i][j].toLong()
                else res *= input1[i][j].toLong()
            }
            res1 += res
        }
        println(res1)
    }
    run {
        var res2 = 0L
        var lastOp = input.last().length+1
        for (j0 in input.last().indices.reversed()) {
            val op = input.last()[j0]
            if (op == ' ') continue
            val numbers = (lastOp-2 downTo j0).map { j1 ->
                var num = 0L
                for (i in 0 until input.size-1) {
                    val c = input[i][j1]
                    if (c == ' ') continue
                    num = num * 10 + (c-'0')
                }
                num
            }
            val res = numbers.reduce { a, b -> if (op == '+') a + b else a * b }
            res2 += res
            lastOp = j0
        }
        println(res2)
    }
}
