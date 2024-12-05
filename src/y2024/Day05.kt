package y2024

import readInput

fun main() {
    val input = readInput("Day05")
    val inputi = input.indexOf("")
    val ordering = input.subList(0, inputi).map {
        it.split('|')
    }
    val orderingMap = ordering.groupingBy { it[0] }.fold({ _, _ -> HashSet<String>() }) { _, acc, e ->
        acc.also { acc.add(e[1]) }
    }
    val updates = input.subList(inputi+1, input.size).map {
        it.split(',')
    }

    fun validate(update: List<String>): Boolean {
        val printed = HashSet<String>()
        for (page in update) {
            val after = orderingMap[page] ?: emptySet()
            if (after.any { it in printed }) return false
            printed.add(page)
        }
        return true
    }
    run {
        var res1 = 0L
        for (update in updates) {
            if (validate(update)) {
                res1 += update[update.size/2].toLong()
            }
        }
        println(res1)
    }
    run {
        var res2 = 0L
        for (update in updates) {
            if (!validate(update)) {
                val correct = mutableListOf<String>()
                for (page in update) {
                    val wrongI = correct.indexOfFirst { it ->
                        it in (orderingMap[page] ?: emptySet())
                    }
                    if (wrongI >= 0) {
                        correct.add(wrongI, page)
                    } else {
                        correct.add(page)
                    }
                }
                res2 += correct[correct.size/2].toLong()
            }
        }
        println(res2)
    }
}
