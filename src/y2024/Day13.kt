package y2024

import readInput
import java.math.BigInteger

fun main() {
    for (part2 in listOf(false, true)) {
        var res = BigInteger.ZERO
        readInput("Day13").let { input ->
            (input.indices step 4).map { i ->
                val (ax,ay) = input[i].split("Button A: X+", ", Y+").filter { it.isNotEmpty() }.map { it.toBigInteger() }
                val (bx,by) = input[i+1].split("Button B: X+", ", Y+").filter { it.isNotEmpty() }.map { it.toBigInteger() }
                val (px,py) = input[i+2].split("Prize: X=", ", Y=").filter { it.isNotEmpty() }.map {
                    if (part2) {
                        it.toBigInteger() + BigInteger.valueOf(10000000000000)
                    } else {
                        it.toBigInteger()
                    }
                }
                val d = (ax*by - ay*bx)
                if (d == BigInteger.ZERO || bx == BigInteger.ZERO) error("TODO")
                val (a, ra) = (px*by - py*bx).divideAndRemainder(d)
                if (ra != BigInteger.ZERO) return@map
                val (b, rb) = (px - a * ax).divideAndRemainder(bx)
                if (rb != BigInteger.ZERO) return@map
                val r = BigInteger.valueOf(3) * a + b
                //println("$a $b $r")
                res += r
            }
        }
        println(res)
    }
}
