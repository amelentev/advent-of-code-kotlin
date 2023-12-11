import kotlin.math.abs

fun main() {
    val input = readInput("Day11")
    val (n, m) = input.size to input[0].length
    val expandY = input.map { line -> line.all { it=='.' } }
    val expandX = (0 until m).map { j -> input.all { it[j] == '.' } }
    fun countExpand(x1: Int, x2: Int, expandX: List<Boolean>): Int {
        if (x1 > x2) return countExpand(x2, x1, expandX)
        return (x1..x2).count { expandX[it] }
    }

    val galaxies = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (input[i][j] == '#') galaxies.add(i to j)
        }
    }
    fun solve(expand: Long): Long {
        var res = 0L
        for ((i1, j1) in galaxies) {
            for ((i2, j2) in galaxies) {
                res += abs(i1-i2) + abs(j1-j2)
                res += (expand-1) * countExpand(i1, i2, expandY)
                res += (expand-1) * countExpand(j1, j2, expandX)
            }
        }
        return res/2
    }
    println(solve(2))
    println(solve(10))
    println(solve(100))
    println(solve(1000000))
}