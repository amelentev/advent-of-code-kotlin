private sealed interface Elem : Comparable<Elem> {
    data class EInt(val v: Int) : Elem {
        override fun toString() = v.toString()
    }
    data class EList(val lst: List<Elem>) : Elem {
        override fun toString() = "[${lst.joinToString(",")}]"
    }

    override fun compareTo(other: Elem): Int {
        when {
            this is EInt && other is EInt -> return this.v.compareTo(other.v)
            this is EList && other is EList -> {
                for (i in 0 until maxOf(this.lst.size, other.lst.size)) {
                    when {
                        i >= this.lst.size -> return -1
                        i >= other.lst.size -> return 1
                        else -> {
                            val r = this.lst[i].compareTo(other.lst[i])
                            if (r != 0) return r
                        }
                    }
                }
                return 0
            }
            this is EInt -> return EList(listOf(this)).compareTo(other)
            other is EInt -> return this.compareTo(EList(listOf(other)))
            else -> error("")
        }
    }

    companion object {
        fun parseList(s: String, start: Int): Pair<EList, Int> {
            val res = mutableListOf<Elem>()
            var i = start
            while (i < s.length) {
                when (s[i]) {
                    ',' -> i++
                    '[' -> {
                        val (lst, end) = parseList(s, i+1)
                        res.add(lst)
                        i = end
                    }
                    ']' -> {
                        return EList(res) to i+1
                    }
                    else -> {
                        val ni = s.indexOfAny(charArrayOf(',', ']'), i)
                        res.add(EInt(s.substring(i, ni).toInt()))
                        i = ni
                    }
                }
            }
            error("")
        }
    }
}
fun main() {
    val input = readInput("Day13")
    var res1 = 0
    val res2 = mutableListOf<Elem>()

    for (i in input.indices step 3) {
        val (s1) = Elem.parseList(input[i], 1)
        val (s2) = Elem.parseList(input[i+1], 1)
        println(s1)
        println(s2)
        println(s1.compareTo(s2))
        if (s1 <= s2) res1 += (i/3)+1
        res2.add(s1)
        res2.add(s2)
    }
    println(res1)
    val (div1) = Elem.parseList("[[2]]", 1)
    val (div2) = Elem.parseList("[[6]]", 1)
    res2.addAll(listOf(div1, div2))
    res2.sort()
    println((res2.indexOf(div1)+1) * (res2.indexOf(div2)+1))
}
