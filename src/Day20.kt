import kotlin.system.exitProcess

fun main() {
    val input = readInput("Day20").associate {
        val (name, out) = it.split(" -> ")
        name to out.split(", ").toSet()
    }
    fun withSign(s: String) = when {
        input.keys.contains("%$s") -> "%$s"
        input.keys.contains("&$s") -> "&$s"
        else -> s
    }
    println("flowchart TD")
    println(input.entries.sortedBy { if (it.key == "broadcaster") 0 else if (it.key == "&vr") 2 else 1 }.flatMap { (from, to) ->
        to.map {
            "$from --> ${withSign(it)}{${withSign(it)}}"
        }
    }.joinToString("\n"))

    val flipFlop = HashSet<String>()
    val conjLow = input.keys.filter { it.startsWith("&") }.map { it.removePrefix("&") }.associateWith { conj ->
        input.filter { conj in it.value }.map { it.key.removePrefix("&").removePrefix("%") }.toMutableSet()
    }
    var res1 = 0
    var res2 = 0
    for (step in 1..1000) {
        val queue = ArrayDeque<Triple<String, String, Boolean>>()
        fun send(from: String, to: Collection<String>, v: Boolean) = to.forEach {
            queue.add(Triple(from, it, v))
            if (v) res2++
            else res1++
            if (it == "rx" && !v) {
                println("2:$step")
                exitProcess(0)
            }
        }
        send("button", listOf("broadcaster"), false)
        while (queue.isNotEmpty()) {
            val (s0,s1,v) = queue.removeFirst()
            input[s1]?.let { s2 ->
                send(s1, s2, v)
            }
            input["%$s1"]?.let { s2 ->
                if (!v) {
                    if (flipFlop.contains(s1)) {
                        flipFlop.remove(s1)
                        send(s1, s2, false)
                    } else {
                        flipFlop.add(s1)
                        send(s1, s2, true)
                    }
                }
            }
            input["&$s1"]?.let { s2 ->
                val inputs = conjLow[s1]!!
                if (v) {
                    inputs.remove(s0)
                } else {
                    inputs.add(s0)
                }
                send(s1, s2, inputs.isNotEmpty())
            }
        }
    }
    println(res1.toLong() * res2)

    val res3 = input["broadcaster"]!!.map { start ->
        var cycle = 0
        var p = 1
        var cur = start
        while (true) {
            val tos = input["%$cur"]!!
            if (tos.size == 2) {
                cycle = cycle or p
                cur = tos.find { "%$it" in input  }!!
            } else {
                if ("%"+tos.first() !in input) break
                cur = tos.first()
            }
            p = p shl 1
        }
        cycle or p
    }.fold(1L) { a, b -> a * b}
    println(res3)
}
