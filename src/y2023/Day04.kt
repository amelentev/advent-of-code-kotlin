fun main() {
    val input = readInput("Day04")
    var res1 = 0L
    val count = LongArray(input.size) { 1 }
    for (i in input.indices) {
        val line = input[i]
        val winning = line.substringAfter(':').substringBefore('|').trim().split(" ").filter { it.isNotBlank() }.toSet()
        val my = line.substringAfter('|').trim().split(" ").filter { it.isNotBlank() }.toSet()
        val wins = my.count { it in winning }
        if (wins > 0) {
            res1 += 1L shl (wins - 1)
        }
        for (j in i+1 until minOf(input.size, i+1+wins)) {
            count[j] += count[i]
        }
    }
    println(res1)
    println(count.sum())
}
