fun main() {
    val input = readInput("Day23")
    fun solve(part2: Boolean): Int {
        val map = input.map { it.toCharArray() }
        var res = 0
        fun dfs(i: Int, j: Int, d: Int) {
            if (i !in map.indices || j !in map[i].indices || map[i][j] == '#' || map[i][j] == 'o') return
            if (i == map.lastIndex && j == map[i].lastIndex-1) {
                if (res < d) {
                    res = d
                    println(res)
                }
                return
            }
            val c = map[i][j]
            map[i][j] = 'o'
            if (part2) {
                dfs(i, j+1, d+1)
                dfs(i+1, j, d+1)
                dfs(i, j-1, d+1)
                dfs(i-1, j, d+1)
            } else {
                when (c) {
                    '>' -> dfs(i, j+1, d+1)
                    'v' -> dfs(i+1, j, d+1)
                    '<' -> dfs(i, j-1, d+1)
                    '^' -> dfs(i+1, j, d+1)
                    '.' -> {
                        dfs(i, j+1, d+1)
                        dfs(i+1, j, d+1)
                        dfs(i, j-1, d+1)
                        dfs(i-1, j, d+1)
                    }
                    else -> error(c)
                }
            }
            map[i][j] = c
        }
        dfs(0, 1, 0)
        return res
    }
    println("1:\t" + solve(false))
    println("2:\t" + solve(true))
}