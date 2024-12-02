fun main() {
    fun solve1(pattern: String, arr: List<Int>): Long {
        val dp = Array(pattern.length+1) { LongArray(arr.size+1) }
        dp[0][0] = 1
        for (i in dp.indices) {
            for (j in dp[i].indices) {
                if (dp[i][j] <= 0) continue
                if (i in pattern.indices && pattern[i] != '#') {
                    dp[i+1][j] += dp[i][j]
                }
                if (j in arr.indices) {
                    val end = i + arr[j]
                    if ((i until end).all { it in pattern.indices && pattern[it] != '.' } &&
                        (end == pattern.length || pattern[end] != '#'))
                    {
                        dp[end+1][j+1] += dp[i][j]
                    }
                }
            }
        }
        return dp[pattern.length][arr.size]
    }
    val input = readInput("Day12")
    var res1 = 0L
    var res2 = 0L
    for (line in input) {
        val pattern = line.substringBefore(' ')
        val arr = line.substringAfter(' ').split(',').map { it.toInt() }
        res1 += solve1("$pattern.", arr)
        val pattern2 = (1..5).joinToString("?") { pattern }
        val arr2 = (1..5).flatMap { arr }
        res2 += solve1("$pattern2.", arr2)
    }
    println(res1)
    println(res2)
}