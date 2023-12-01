fun main() {
    val input = readInput("Day06")
    fun findCode(s: String, n: Int): Int =
        (0 .. s.length-n).find { i ->
            s.subSequence(i until i+n).toSet().size == n
        }!! + n

    for (s in input) {
        println(findCode(s, 4))
    }
    println("part2")
    for (s in input) {
        println(findCode(s, 14))
    }
}
