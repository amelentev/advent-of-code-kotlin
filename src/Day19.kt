fun main() {
    val input = readInput("Day19")
    data class Rule(val c: Char, val cmp: Char, val v: Int, val to: String)
    data class Workflow(val name: String, val rules: List<Rule>, val elseTo: String)
    val workflows = input.take(input.indexOfFirst { it.isEmpty() }).map {
        val name = it.substringBefore('{')
        val rules = it.substringAfter('{').substringBefore('}').split(",")
        Workflow(
            name = name,
            rules = rules.dropLast(1).map { rule ->
                Rule(
                    c = rule[0],
                    cmp = rule[1],
                    v = rule.substring(2).substringBefore(':').toInt(),
                    to = rule.substringAfter(':')
                )
            },
            elseTo = rules.last()
        )
    }.associateBy { it.name }

    val parts = input.drop(input.indexOfFirst { it.isEmpty() }+1).map { part ->
        part.trim('{', '}').split(',').associate {
            it[0] to it.substring(2).toLong()
        }
    }
    fun process(part: Map<Char, Long>, w: Workflow): String {
        for (r in w.rules) {
            var diff = part[r.c]!! - r.v
            if (r.cmp == '<') diff = -diff
            if (diff > 0) return r.to
        }
        return w.elseTo
    }
    fun solve1(part: Map<Char, Long>): Boolean {
        var w = workflows["in"]!!
        while (true) {
            val next = process(part, w)
            when (next) {
                "A" -> return true
                "R" -> return false
            }
            w = workflows[next]!!
        }
    }
    var res1 = 0L
    for (part in parts) {
        if (solve1(part)) {
            res1 += part.values.sum()
        }
    }
    println(res1)

    var res2 = 0L
    fun dfs(workflow: String, range: Map<Char, Pair<Int, Int>>) {
        if (workflow == "A") {
            res2 += range.values.fold(1L) { a, x -> a * (x.second - x.first + 1) }
            return
        } else if (workflow == "R" || range.values.any { it.first > it.second }) {
            return
        }
        val w = workflows[workflow]!!
        var r = range
        for (rule in w.rules) {
            val (x1, x2) = r[rule.c]!!
            val (rt,rf) = if (rule.cmp == '<') {
                (x1 to minOf(x2, rule.v-1)) to (maxOf(minOf(x2, rule.v-1)+1, x1) to x2)
            } else {
                (maxOf(x1, rule.v+1) to x2) to (x1 to minOf(maxOf(x1, rule.v+1)-1, x2))
            }
            dfs(rule.to, r + mapOf(rule.c to rt))
            r = r + mapOf(rule.c to rf)
        }
        dfs(w.elseTo, r)
    }
    dfs("in", mapOf(
        'x' to (1 to 4000),
        'm' to (1 to 4000),
        'a' to (1 to 4000),
        's' to (1 to 4000),
    ))
    println(res2)
}
