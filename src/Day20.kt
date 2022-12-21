fun main() {
    fun mix(input: Array<Pair<Long, Int>>) {
        val n = input.size
        for (idx in 0 until n) {
            val oldI = input.indices.find { input[it].second == idx }!!
            var newI = (oldI + input[oldI].first) % (n-1)
            newI = (newI + n-1) % (n-1)
            if (newI == 0L) newI = n-1L
            if (oldI < newI) {
                val o = input[oldI]
                for (i in oldI until newI.toInt()) {
                    input[i] = input[i+1]
                }
                input[newI.toInt()] = o
            } else {
                val o = input[oldI]
                for (i in oldI downTo  newI.toInt()+1) {
                    input[i] = input[i-1]
                }
                input[newI.toInt()] = o
            }
            //println(input.joinToString(",") { it.first.toString() })
        }
    }
    val file = "Day20"
    run {
        val input = readInput(file).mapIndexed() { idx, v -> v.toLong() to idx }.toTypedArray()
        val n = input.size
        mix(input)
        val idx0 = input.indices.find { input[it].first == 0L }!!
        val res1 = input[(idx0 + 1000) % n].first + input[(idx0 + 2000) % n].first + input[(idx0 + 3000) % n].first
        println(res1)
    }
    run {
        val input = readInput(file).mapIndexed() { idx, v -> 811589153L * v.toLong() to idx }.toTypedArray()
        val n = input.size
        repeat(10) {
            println("Round${it+1}")
            mix(input)
        }
        val idx0 = input.indices.find { input[it].first == 0L }!!
        val res2 = input[(idx0 + 1000) % n].first + input[(idx0 + 2000) % n].first + input[(idx0 + 3000) % n].first
        println(res2)
    }
}
