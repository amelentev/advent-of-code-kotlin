fun main() {
    val input = readInput("Day09")
    fun solve1(seq: List<Long>): Long {
        if (seq.all { it == 0L }) return 0
        val res = solve1(seq.zipWithNext { a,b -> b - a })
        return seq.last() + res
    }
    fun solve2(seq: List<Long>): Long {
        if (seq.all { it == 0L }) return 0
        val res = solve2(seq.zipWithNext { a,b -> b - a })
        return seq.first() - res
    }
    val seqs = input.map { it.split(" ").map { it.toLong() } }
    println(seqs.sumOf { solve1(it) })
    println(seqs.sumOf { solve2(it) })
}