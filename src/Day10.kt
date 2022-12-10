import kotlin.math.abs

fun main() {
    val input = readInput("Day10")
    var X = 1L
    var cycle = 0
    var res1 = 0L
    val screen = CharArray(240) { '.' }
    fun check(x: Long, c: Int) {
        if ((c-20) % 40 == 0) res1 += c * X
        if (abs(x-((c-1)%40)) <= 1) screen[c-1] = '#'
    }
    for (s in input) {
        when (s) {
            "noop" -> {
                check(X, cycle+1)
                cycle++
            }
            else -> {
                val v = s.removePrefix("addx ").toLong()
                check(X, cycle+1)
                check(X, cycle+2)
                X += v
                cycle += 2
            }
        }
    }
    while (cycle < 240) {
        check(X, ++cycle)
    }
    println("$cycle $X")
    println(res1)
    String(screen).let {
        for (i in 0..200 step 40) {
            println(it.subSequence(i, i+40))
        }
    }
}
