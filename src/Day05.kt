fun main() {
    val input = readInput("Day05")
    val stacksInput = input.takeWhile { it != "" }.dropLast(1)
    val n = (stacksInput[0].length + 1) / 4
    val stacks1 = Array(n) { ArrayDeque<Char>() }
    val stacks2 = Array(n) { ArrayDeque<Char>() }
    for (s in stacksInput) {
        for (i in 0 until n) {
            val c = s[1 + 4*i]
            if (c != ' ') {
                stacks1[i].addFirst(c)
                stacks2[i].addFirst(c)
            }
        }
    }
    val input2 = input.drop(stacksInput.size + 2)
    for (s in input2) {
        val (times, from, to) = s.split("move ", " from ", " to ").drop(1).map { it.toInt() }
        val s2 = mutableListOf<Char>()
        repeat(times) {
            val c = stacks1[from-1].removeLast()
            stacks1[to-1].addLast(c)
            s2.add(stacks2[from-1].removeLast())
        }
        for (c in s2.reversed()) {
            stacks2[to-1].addLast(c)
        }
    }
    println(stacks1.joinToString("") { it.last().toString() })
    println(stacks2.joinToString("") { it.last().toString() })
}
