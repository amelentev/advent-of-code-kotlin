fun main() {
    val input = readInput("Day08")
    var res1 = 0
    var res2 = 0
    for (i0 in input.indices) {
        for (j0 in input[i0].indices) {
            val t = input[i0][j0]
            val visible =
                (0 until i0).all { input[it][j0] < t } ||
                (i0+1 until input.size).all { input[it][j0] < t } ||
                (0 until j0).all { input[i0][it] < t } ||
                (j0+1 until input[i0].length).all { input[i0][it] < t }
            if (visible) res1 ++

            fun calcDist(r: IntProgression, f: (Int) -> Char): Int {
                var res = 0
                for (i in r) {
                    if (f(i) < t) res++
                    else {
                        res++
                        break
                    }
                }
                return res
            }
            var r2 = calcDist(i0-1 downTo 0) { input[it][j0] }
            r2 *= calcDist(i0+1 until input.size) { input[it][j0] }
            r2 *= calcDist(j0-1 downTo 0) { input[i0][it] }
            r2 *= calcDist(j0+1 until input[i0].length) { input[i0][it] }
            res2 = maxOf(res2, r2)
        }
    }
    println(res1)
    println(res2)
}
