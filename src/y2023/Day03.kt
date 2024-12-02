fun main() {
    val input = readInput("Day03")
    val n = input.size
    val m = input[0].length
    var res1 = 0L
    var res2 = 0L
    fun isPart(i: Int, j: Int): Pair<Int, Int>? {
        for (i1 in i-1..i+1) {
            for (j1 in j-1 .. j+1) {
                if (i1 in 0 until n && j1 in 0 until m) {
                    val c = input[i1][j1]
                    if (c != '.' && !c.isDigit()) return i1 to j1
                }
            }
        }
        return null
    }
    val gears = mutableMapOf<Pair<Int, Int>, Long>()
    for (i in input.indices) {
        val line = input[i]
        var x = 0L
        var isPart: Pair<Int, Int>? = null
        for (j in line.indices) {
            if (line[j].isDigit()) {
                x = x * 10 + line[j].toString().toLong()
                isPart = isPart ?: isPart(i,j)
            }
            if (!line[j].isDigit() || j == line.lastIndex) {
                if (isPart != null) {
                    res1 += x
                    if (input[isPart.first][isPart.second] == '*') {
                        val prev = gears[isPart]
                        if (prev != null) {
                            res2 += prev * x
                        } else {
                            gears[isPart] = x
                        }
                    }
                }
                x = 0
                isPart = null
            }
        }
    }
    println(res1)
    println(res2)
}