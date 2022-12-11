import java.math.BigInteger

fun main() {
    val input = readInput("Day11")
    data class Monkey(
        val id: Int,
        val items: ArrayDeque<BigInteger>,
        val op: (BigInteger) -> BigInteger,
        val testDiv: BigInteger,
        val pass: (BigInteger) -> Int,
        var inspected: Long = 0,
    )
    fun readMonkeys() = (input.indices step 7).map { i ->
        assert(input[i] == "Monkey ${i}:")
        val items = input[i+1].trim().removePrefix("Starting items: ").split(", ").map { it.toBigInteger() }
        val op = input[i+2].trim().removePrefix("Operation: new = old ")
        val testDiv = input[i+3].trim().removePrefix("Test: divisible by ").toBigInteger()
        val testTrue = input[i+4].trim().removePrefix("If true: throw to monkey ").toInt()
        val testFalse = input[i+5].trim().removePrefix("If false: throw to monkey ").toInt()
        Monkey(
            id = i,
            items = ArrayDeque(items),
            op = when {
                op == "* old" -> { { it * it} }
                op == "+ old" -> { { it + it} }
                op[0] == '+' -> { {it + op.removePrefix("+ ").toBigInteger()} }
                op[0] == '*' -> { {it * op.removePrefix("* ").toBigInteger()} }
                else -> error(op)
            },
            testDiv = testDiv,
            pass = {
                if (it.mod(testDiv) == BigInteger.ZERO) testTrue else testFalse
            }
        )
    }
    run {
        val monkeys = readMonkeys()
        repeat(20) {
            for (m in monkeys) {
                while (m.items.isNotEmpty()) {
                    val w = m.items.removeFirst()
                    val newW = m.op(w) / 3.toBigInteger()
                    m.inspected++
                    monkeys[m.pass(newW)].items.addLast(newW)
                }
            }
        }
        val res1 =  monkeys.map { it.inspected }.sortedDescending().take(2).let { it[0] * it[1] }
        println(res1)
    }
    run {
        val monkeys = readMonkeys()
        val lcm = monkeys.map { it.testDiv }.reduce { a, b -> a * b / a.gcd(b) }
        repeat(10_000) {
            for (m in monkeys) {
                while (m.items.isNotEmpty()) {
                    val w = m.items.removeFirst()
                    val newW = m.op(w).mod(lcm)
                    m.inspected++
                    monkeys[m.pass(newW)].items.addLast(newW)
                }
            }
            if (it == 19 || it % 1000 == 0) println( monkeys.map { it.inspected } )
        }
        val res2 =  monkeys.map { it.inspected }.sortedDescending().take(2).let { it[0] * it[1] }
        println(res2)
    }
}
