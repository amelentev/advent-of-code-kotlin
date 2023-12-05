fun main() {
    val input = readInput("Day05")
    run {
        var seeds = input[0].substringAfter("seeds: ").split(' ').map { it.toLong() }.toMutableSet()
        var next = mutableSetOf<Long>()
        var i = 3
        while (i < input.size) {
            if (input[i].isBlank()) {
                seeds = (seeds + next).toMutableSet()
                next = mutableSetOf()
                i+=2
                continue
            }
            val (d,s,l) = input[i].split(' ').map { it.toLong() }
            val affected = seeds.filter { s <= it && it < s + l }.toSet()
            seeds.removeAll(affected)
            next.addAll(affected.map { it - s + d })
            i++
        }
        println((seeds + next).min())
    }
    run {
        var seeds = input[0].substringAfter("seeds: ").split(' ').map { it.toLong() }.let {
            (it.indices step 2).map { i ->
                it[i] to (it[i]+it[i+1])
            }
        }.toMutableSet()
        var next = mutableSetOf<Pair<Long, Long>>()
        var i = 3
        while (i < input.size) {
            if (input[i].isBlank()) {
                seeds = (seeds + next).toMutableSet()
                next = mutableSetOf()
                i+=2
                continue
            }
            val (d,s,l) = input[i].split(' ').map { it.toLong() }
            val queue = ArrayDeque(seeds)
            while (queue.isNotEmpty()) {
                val r = queue.removeFirst()
                val n = maxOf(s, r.first) to minOf(s+l, r.second)
                if (n.first < n.second) {
                    seeds.remove(r)
                    next.add(n.first - s + d to n.second - s + d)
                    val n1 = s+l to r.second
                    if (n1.first < n1.second) {
                        seeds.add(n1)
                        queue.add(n1)
                    }
                    val n2 = r.first to s
                    if (n2.first < n2.second) {
                        seeds.add(n2)
                        queue.add(n2)
                    }
                }
            }
            i++
        }
        println((seeds + next).minOf { it.first })
    }
}