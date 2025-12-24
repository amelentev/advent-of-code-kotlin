package y2025

import readInput

fun main() {
    val input = readInput("Day11").associate { line ->
        line.substringBefore(":") to line.substringAfter(": ").split(" ")
    }
    fun findPaths(src: String, dst: String): Long {
        val res = mutableMapOf<String, Long>(src to 1)
        val queue = ArrayDeque<String>(listOf(src))
        var dstRes = 0L
        while (queue.isNotEmpty()) {
            val u = queue.removeFirst()
            val ru = res[u]!!
            for (v in input[u] ?: emptyList()) {
                val oldRv = (res[v] ?: 0)
                res[v] = oldRv + ru
                if (oldRv == 0L) queue.add(v)
            }
            if (u == dst) dstRes += ru
            res[u] = 0
        }
        return dstRes
    }
    println(findPaths("you", "out"))

    /*println(findPaths("svr", "dac"))
    println(findPaths("dac", "fft"))
    println(findPaths("fft", "out"))
    println(findPaths("svr", "fft"))
    println(findPaths("fft", "dac"))
    println(findPaths("dac", "out"))*/
    val r1 = findPaths("svr", "dac") * findPaths("dac", "fft") * findPaths("fft", "out")
    val r2 = findPaths("svr", "fft") * findPaths("fft", "dac") * findPaths("dac", "out")
    println(r1 + r2)
}
