package y2024

import readInput

fun main() {
    val input = readInput("Day23").map {
        it.split("-")
    }
    val D = HashMap<String, HashSet<String>>()
    fun addEdge(u1: String, u2: String) {
        D.getOrPut(u1) { HashSet() }.add(u2)
    }
    for ((u1, u2) in input) {
        addEdge(u1, u2)
        addEdge(u2, u1)
    }
    val V = D.keys.filter { D[it]!!.size >= 3}.sorted()
    run {
        var res1 = 0
        for (i1 in V.indices) {
            val u1 = V[i1]
            for (i2 in i1+1 until V.size) {
                val u2 = V[i2]
                if (!D[u1]!!.contains(u2)) continue
                for (i3 in i2+1 until V.size) {
                    val u3 = V[i3]
                    if (u1.startsWith("t") || u2.startsWith("t") || u3.startsWith("t")) {
                        if (!D[u1]!!.contains(u3) || !D[u2]!!.contains(u3)) continue
                        res1++
                    }
                }
            }
        }
        println(res1)
    }
    run {
        println(D.values.maxOf { it.size })
        var bestClique = hashSetOf(V.first())
        for (u1 in V) {
            val clique = hashSetOf(u1)
            while (true) {
                var added = false
                for (u2 in V) {
                    if (u2 in clique) continue
                    if (clique.all { u2 in D[it]!! }) {
                        clique.add(u2)
                        added = true
                    }
                }
                if (!added) break
            }
            if (clique.size > bestClique.size) {
                bestClique = clique
            }
        }
        println(bestClique.size)
        println(bestClique.sorted().joinToString(","))
    }
}
