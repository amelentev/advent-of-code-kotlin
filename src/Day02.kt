fun main() {
    val input = readInput("Day02")
    val set1 = mapOf(
        "red" to 12L,
        "green" to 13L,
        "blue" to 14L,
    )
    var res1 = 0L
    var res2 = 0L
    for (line in input) {
        val id = line.removePrefix("Game ").substringBefore(':').toLong()
        val cases = line.substringAfter(':').split(";")
        var ok = true
        val r2 = mutableMapOf<String, Long>()
        for (case in cases) {
            for (c in case.split(",").map { it.trim() }) {
                val color = c.substringAfter(' ')
                val n = c.substringBefore(' ').toLong()
                ok = ok && set1[color]!! >= n
                r2[color] = maxOf(r2[color] ?: 0, n)
            }
        }
        if (ok) res1 += id
        res2 += (r2["red"] ?: 0L) * (r2["green"] ?: 0) * (r2["blue"] ?: 0)
    }
    println(res1)
    println(res2)
}