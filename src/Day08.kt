import java.math.BigInteger

fun main() {
    val input = readInput("Day08")
    val instructions = input[0]
    val map = input.drop(2).associate {
        val key = it.substringBefore(' ')
        val left = it.substringAfter('(').substringBefore(',')
        val right = it.substringAfter(", ").substringBefore(')')
        key to (left to right)
    }
    fun next(s: String, i: Int): String {
        return if (instructions[i % instructions.length] == 'L') {
            map[s]!!.first
        } else {
            map[s]!!.second
        }
    }
    run {
        var cur = "AAA"
        var res = 0
        while (cur != "ZZZ") {
            cur = next(cur, res)
            res ++
        }
        println(res)
    }
    run {
        fun find(s0: String, i0: Int): Pair<Int, String> {
            var i = i0
            var s = s0
            do {
                s = next(s, i)
                i++
            } while (!s.endsWith('Z'))
            return i to s
        }
        val starts = map.keys.filter { it.endsWith('A') }
        for (s0 in starts) {
            print(s0)
            var (s1, i1) = s0 to 0
            for (i in 0..3) {
                val next = find(s1, i1)
                s1 = next.second
                print("\t${next.first - i1}\t$s1")
                i1 = next.first
            }
            println()
        }
        val cycles = starts.map { find(it, 0).first }
        val lcm = cycles.fold(BigInteger.ONE) { acc, x ->
            acc * x.toBigInteger() / acc.gcd(x.toBigInteger())
        }
        println(lcm)
    }
}