enum class HandType {
    K5,
    K4,
    FH,
    K3,
    P2,
    P1,
    HC
}

val cardOrder1 = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(", ").map { it[0] }
val cardOrder2 = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ").map { it[0] }

fun getHandType1(h: String): HandType {
    val cc = h.groupingBy { it }.eachCount().values.toList().sortedDescending()
    return when {
        cc[0] == 5 -> HandType.K5
        cc[0] == 4 -> HandType.K4
        cc == listOf(3, 2) -> HandType.FH
        cc[0] == 3 -> HandType.K3
        cc == listOf(2, 2, 1) -> HandType.P2
        cc == listOf(2, 1, 1, 1) -> HandType.P1
        cc == listOf(1, 1, 1, 1, 1) -> HandType.HC
        else -> error("impossible")
    }
}

fun getHandType2(h: String): HandType {
    return cardOrder2.minOf { jc -> getHandType1(h.replace('J', jc)) }
}

fun main() {
    val input = readInput("Day07")

    fun cmpCards(cardOrder: List<Char>) = Comparator<String> { s1, s2 ->
        for (i in s1.indices) {
            val r = cardOrder.indexOf(s1[i]) - cardOrder.indexOf(s2[i])
            if (r != 0) return@Comparator r
        }
        return@Comparator 0
    }
    fun solve(cmp: Comparator<String>) {
        val handsSorted = input.map { it.substringBefore(' ') }.sortedWith(cmp.reversed())
        var res = 0L
        for (line in input) {
            val hand = line.substringBefore(' ')
            val rank = handsSorted.indexOf(hand) + 1
            val value = line.substringAfter(' ').toLong()
            res += rank * value
        }
        println(res)
    }
    solve(compareBy<String> { getHandType1(it) }.thenComparing(cmpCards(cardOrder1)))
    solve(compareBy<String> { getHandType2(it) }.thenComparing(cmpCards(cardOrder2)))
}
