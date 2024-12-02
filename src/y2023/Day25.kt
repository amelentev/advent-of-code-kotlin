fun globalMinCut(mat: Array<IntArray>): Pair<Int, List<Int>>  {
    var best = Int.MAX_VALUE to emptyList<Int>()
    val n = mat.size
    val co = Array(n) { mutableListOf(it) }
    for (ph in 1 until n) {
        val w = mat[0].copyOf()
        var (s, t) = 0 to 0
        for (it in 0 until n - ph) {
            w[t] = Int.MIN_VALUE
            s = t
            t = w.indices.maxBy { w[it] }
            for (i in 0 until n) w[i] += mat[t][i]
        }
        if (best.first > w[t] - mat[t][t]) {
            best = w[t] - mat[t][t] to co[t]
        }
        co[s].addAll(co[t])
        for (i in 0 until n) mat[s][i] += mat[t][i]
        for (i in 0 until n) mat[i][s] = mat[s][i]
        mat[0][t] = Int.MIN_VALUE
    }
    return best
}

fun main() {
    fun read(file: String) = readInput(file).associate { line ->
        val from = line.substringBefore(':')
        val to = line.substringAfter(": ").split(" ")
        /*for (t in to) {
            println("$from --> $t{$t}")
        }*/
        from to to.toMutableSet()
    }.toMutableMap().also { graph ->
        for ((v1,edges) in graph.entries.toList()) {
            for (v2 in edges) {
                graph.getOrPut(v2) {
                    mutableSetOf()
                }.add(v1)
            }
        }
    }
    fun solve1(file: String): Int {
        val graph = read(file)
        val names = (graph.keys + graph.values.flatten()).toSet().withIndex().associate { it.index to it.value }
        val n = names.size
        val mat = Array(n) { i ->
            IntArray(n) { j ->
                if (graph[names[i]!!]?.contains(names[j]!!) == true || graph[names[j]!!]?.contains(names[i]!!) == true) 1 else 0
            }
        }
        val (cost, cut) = globalMinCut(mat)
        assert(cost == 3)
        return cut.size * (names.size - cut.size)
    }
    assert(solve1("Day25t") == 54)
    println(solve1("Day25"))
}