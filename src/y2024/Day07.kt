package y2024

import readInput

fun main() {
    val input = readInput("Day07").map {
        it.split(": ", " ").map { it.toLong() }
    }
    fun check(line: List<Long>, part2: Boolean): Boolean {
        val target = line[0]
        var set0 = hashSetOf(line[1])
        for (i in 2 until line.size) {
            val set1 = hashSetOf<Long>()
            val cur = line[i]
            for (prev in set0) {
                if (cur + prev <= target) {
                    set1.add(cur + prev)
                }
                if (cur * prev <= target) {
                    set1.add(cur * prev)
                }
                if (part2) {
                    val v = (prev.toString() + cur.toString()).toBigInteger()
                    if (v <= target.toBigInteger()) {
                        set1.add(v.toLong())
                    }
                }
            }
            set0 = set1
        }
        return set0.contains(target)
    }
    for (part2 in listOf(false, true)) {
        run {
            var res = 0L
            for (line in input) {
                if (check(line, part2)) {
                    res += line[0]
                }
            }
            println(res)
        }
    }
}
