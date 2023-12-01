fun main() {
    val input = readInput("Day04")
    val res1 = input.count { line ->
        val (s1,e1, s2,e2) = line.split(',', '-').map { it.toLong() }
        when {
            s1 <= s2 && e2 <= e1 -> true
            s2 <= s1 && e1 <= e2 -> true
            else -> false
        }
    }
    val res2 = input.count { line ->
        val (s1,e1, s2,e2) = line.split(',', '-').map { it.toLong() }
        when {
            s1 in s2..e2 || e1 in s2..e2 -> true
            s2 in s1..e1 || e2 in s1..e1 -> true
            else -> false
        }
    }
    println(res1)
    println(res2)
}
