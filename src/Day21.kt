import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    data class Poly(
        val coef: List<BigDecimal>
    ) {
        operator fun plus(o: Poly): Poly {
            val n = maxOf(coef.size, o.coef.size)
            return Poly(Array(n) { i ->
                coef.getOrElse(i) { BigDecimal.ZERO } + o.coef.getOrElse(i) { BigDecimal.ZERO }
            }.toList())
        }
        operator fun minus(o: Poly): Poly {
            val n = maxOf(coef.size, o.coef.size)
            return Poly(Array(n) { i ->
                coef.getOrElse(i) { BigDecimal.ZERO } - o.coef.getOrElse(i) { BigDecimal.ZERO }
            }.toList())
        }
        operator fun times(o: Poly): Poly {
            val n = coef.size * o.coef.size
            val res = Array(n) { BigDecimal.ZERO }
            for (i in coef.indices) {
                for (j in o.coef.indices) {
                    res[i+j] += coef[i]*o.coef[j]
                }
            }
            return Poly(res.toList())
        }

        operator fun div(o: Poly): Poly {
            if (o.coef.size == 1) {
                return Poly(coef.map { it.divide(o.coef[0], 100, RoundingMode.HALF_UP) })
            }
            error("complex division")
        }
    }
    data class Monkey(
        val name: String,
        val children: List<String> = emptyList(),
        val op: String = "",
        var number: BigDecimal? = null,
        var poly: Poly? = null,
    )
    val monkeys = readInput("Day21").map { line ->
        val (name, task) = line.split(": ")
        val number = task.toBigDecimalOrNull()
        val poly = if (name == "humn") Poly(listOf(BigDecimal.ZERO, BigDecimal.ONE)) else number?.let { Poly(listOf(it)) }
        if (number == null) {
            val args = task.split(" ")
            Monkey(name, children = listOf(args[0], args[2]), op = args[1], poly = poly)
        } else {
            Monkey(name, number = number, poly = poly)
        }
    }.associateBy { it.name }

    fun calcNumber(name: String): BigDecimal {
        val m = monkeys[name]!!
        if (m.number != null) return m.number!!
        m.number = when (m.op) {
            "+" -> calcNumber(m.children[0]) + calcNumber(m.children[1])
            "-" -> calcNumber(m.children[0]) - calcNumber(m.children[1])
            "*" -> calcNumber(m.children[0]) * calcNumber(m.children[1])
            "/" -> calcNumber(m.children[0]) / calcNumber(m.children[1])
            else -> error(m.op)
        }
        return m.number!!
    }
    println(calcNumber("root"))
    fun calcPoly(name: String): Poly {
        val m = monkeys[name]!!
        if (m.poly != null) return m.poly!!
        m.poly = when (m.op) {
            "+" -> calcPoly(m.children[0]) + calcPoly(m.children[1])
            "-" -> calcPoly(m.children[0]) - calcPoly(m.children[1])
            "*" -> calcPoly(m.children[0]) * calcPoly(m.children[1])
            "/" -> calcPoly(m.children[0]) / calcPoly(m.children[1])
            else -> error(m.op)
        }
        return m.poly!!
    }
    val rootArgs = monkeys["root"]!!.children.map { calcPoly(it) }
    println(rootArgs)
    println((rootArgs[1].coef[0] - rootArgs[0].coef[0]) / rootArgs[0].coef[1])
}
