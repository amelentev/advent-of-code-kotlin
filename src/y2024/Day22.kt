package y2024

import readInput

private const val MOD = 16777216L

fun main() {
    val input = readInput("Day22").map { it.toLong() }

    fun rndNext(secret: Long): Long {
        var next = ((secret * 64).xor(secret)) % MOD
        next = (next / 32).xor(next) % MOD
        next = (next * 2048).xor(next) % MOD
        return next
    }
    run {
        var res1 = 0L
        for (secret0 in input) {
            var secret = secret0
            repeat(2000) {
                secret = rndNext(secret)
            }
            res1 += secret
        }
        println(res1)
    }
    run {
        val patterns = input.map { secret0 ->
            var secret = secret0
            val prices = mutableListOf((secret % 10).toInt())
            repeat(2000) {
                secret = rndNext(secret)
                prices.add((secret % 10).toInt())
            }
            val patterns = HashMap<String, Int>()
            for (i in 0 until prices.size-5) {
                val pattern = prices.subList(i, i+5).zipWithNext().map { (a, b) -> b-a }.joinToString(",")
                patterns.putIfAbsent(pattern, prices[i+4])
            }
            patterns
        }
        val allPatterns = patterns.flatMapTo(HashSet()) { it.keys }
        var res2 = 0
        var bestPattern: String? = null
        for (pattern in allPatterns) {
            val res = patterns.sumOf {
                it[pattern] ?: 0
            }
            if (res2 < res) {
                res2 = res
                bestPattern = pattern
            }
        }
        println(res2)
        println(bestPattern)
    }
}
