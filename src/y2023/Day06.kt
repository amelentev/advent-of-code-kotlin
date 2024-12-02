import java.math.BigInteger
import java.util.regex.Pattern
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val input = readInput("Day06")
    var times = input[0].removePrefix("Time:").trim().split(Pattern.compile("\\W+")).map { it.toLong() }
    var distances = input[1].removePrefix("Distance:").trim().split(Pattern.compile("\\W+")).map { it.toLong() }

    times = listOf(times.joinToString("").toLong())
    distances = listOf(distances.joinToString("").toLong())

    var res = BigInteger.ONE
    for (raceNo in times.indices) {
        val T = times[raceNo]
        val d = distances[raceNo]
        // t(T-t) > d, t^2 - tT + d = 0
        val a = 1
        val b = -T
        val c = d
        val D = sqrt(b*b - 4.0*a*c)
        var x1 = (-b - D)/2/a
        var x2 = (-b + D) /2/a
        if (ceil(x1) == x1) {
            x1 += 1
        } else {
            x1 = ceil(x1)
        }
        if (floor(x2) == x2) {
            x2 -= 1
        } else {
            x2 = floor(x2)
        }
        val x = (x2 - x1 + 1).toLong()
        res *= x.toBigInteger()
    }
    println(res)
}
