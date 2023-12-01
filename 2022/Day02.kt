fun main() {
    val input = readInput("Day02")
    var totalScore1 = 0
    var totalScore2 = 0
    for (line in input) {
        val (p1, p2) = line.split(' ')
        val i1 = p1[0] - 'A'
        val i2 = p2[0] - 'X'
        totalScore1 += when (i1) {
            i2 -> 3
            (i2 - 1 + 3) % 3 -> 6
            else -> 0
        } + (i2 + 1)
        totalScore2 += when (i2) {
            0 -> 0 + 1+(i1+2)%3
            1 -> 3 + i1+1
            else -> 6 + 1+(i1+1)%3
        }
    }
    println(totalScore1)
    println(totalScore2)
}
