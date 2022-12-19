fun main() {
    val input = readInput("Day16")
    data class Valve(
        val name: String,
        val rate: Int,
        val leads: List<String>
    )
    val valves = input.map { s ->
        val (valve, rate, leads) = s.split("Valve ", " has flow rate=", "; tunnels lead to valves ", "; tunnel leads to valve ").drop(1)
        Valve(valve, rate.toInt(), leads.split(", ") )
    }.sortedBy { it.rate <= 0 }
    val dist = Array(valves.size) { IntArray(valves.size) { Int.MAX_VALUE/3 } }
    val name2Idx = valves.mapIndexed { i, v -> v.name to i }.associate { it }
    for (i in valves.indices) {
        dist[i][i] = 0
        for (v in valves[i].leads.map { name2Idx[it]!! }) {
            dist[i][v] = 1
        }
    }
    for (k in dist.indices) {
        for (i in dist.indices) {
            for (j in dist.indices) {
                dist[i][j] = minOf(dist[i][j], dist[i][k] + dist[k][j])
            }
        }
    }
    val n = valves.count { it.rate > 0 }

    fun solve(s: Int, mask: Int, time: Int): Int {
        var res = 0
        for (v in 0 until n) {
            val vv = 1 shl v
            if (vv and mask == 0) {
                val t = time - dist[s][v] - 1
                if (t > 0) {
                    res = maxOf(res, solve(v, mask or vv, t) + t * valves[v].rate)
                }
            }
        }
        return res
    }
    val aa = name2Idx["AA"]!!
    val res1 = solve(aa, 0, 30)
    println(res1)

    val fullMask = (1 shl n) - 1
    var res2 = 0
    for (mask in 0 until (fullMask shr 1)) {
        res2 = maxOf(res2, solve(aa, mask, 26) + solve(aa, fullMask xor mask, 26))
    }
    println(res2)
}
