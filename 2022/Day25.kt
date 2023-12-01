fun main() {
    val numbers = mapOf(
        2 to '2',
        1 to '1',
        0 to '0',
        -1 to '-',
        -2 to '=',
    )
    fun decodeDigit(c: Char) = numbers.entries.find { it.value == c }!!.key
    fun encodeDigit(x: Int) = numbers[x]!!
    fun decode(s: String): Long {
        var p = 1L
        var res = 0L
        for (i in s.indices.reversed()) {
            res += p*decodeDigit(s[i])
            p *= 5
        }
        return res
    }
    fun encode(x: Long): String {
        val a = mutableListOf<Long>(0)
        var p = 1L
        while (2*p + a.last() < x) {
            a.add(2*p + a.last())
            p *= 5
        }
        val res = StringBuilder()
        var x1 = x
        while (p > 0) {
            val b = if (x1 >= 0) {
                (x1 + a.last()) / p
            } else {
                (x1 - a.last()) / p
            }
            res.append(encodeDigit(b.toInt()))
            x1 -= b * p
            p /= 5L
            a.removeLast()
        }
        assert(x1 == 0L)
        return res.toString()
    }

    val input = readInput("Day25")
    var res1 = 0L
    for (s in input) {
        val n = decode(s)
        res1 += n
    }
    println(res1)
    println(encode(res1))
}
