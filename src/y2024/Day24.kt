package y2024

import readInput

fun main() {
    data class Gate(val a: String, val op: String, val b: String, var c: String)

    fun part1(inputs: Map<String, Boolean>, gates: List<Gate>): Long {
        val inputs = inputs.toMutableMap()
        val gateInputUnknowns = Array(gates.size) { hashSetOf<String>() }
        val gateQueue = ArrayDeque<Int>()
        val gatesDone = BooleanArray(gates.size) { false }
        for (gateI in gates.indices) {
            val gate = gates[gateI]
            val unknownVars = listOf(gate.a, gate.b).filter { it !in inputs.keys }
            if (unknownVars.isNotEmpty()) {
                gateInputUnknowns[gateI].addAll(unknownVars)
            } else {
                gateQueue.add(gateI)
            }
        }
        val inputVarToGates = gates.flatMapIndexed { i, it -> listOf(it.a to i, it.b to i) }.groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }
        while (gateQueue.isNotEmpty()) {
            val g = gateQueue.removeFirst()
            val gate = gates[g]
            gatesDone[g] = true
            val (in1, in2) = inputs[gate.a]!! to inputs[gate.b]!!
            val res = when (gate.op) {
                "AND" -> in1 && in2
                "OR" -> in1 || in2
                "XOR" -> in1 xor in2
                else -> error("Unknown op $gate")
            }
            if (inputs.putIfAbsent(gate.c, res) == null) {
                for (gateI in inputVarToGates[gate.c] ?: emptyList()) {
                    if (gatesDone[gateI]) continue
                    gateInputUnknowns[gateI].remove(gate.c)
                    if (gateInputUnknowns[gateI].isEmpty()) {
                        gateQueue.add(gateI)
                    }
                }
            }
        }
        val res = inputs.keys.filter { it.startsWith("z") }.sorted().reversed().joinToString("") { if (inputs[it]!!) "1" else "0" }
        return res.toLong(2)
    }
    val (inputs, gates) = readInput("Day24").let { input ->
        val i = input.indexOf("")
        val (inputs, gates) = input.subList(0, i) to input.subList(i+1, input.size)
        val inputsMap = inputs.map { it.split(": ") }.associate { it[0] to (it[1] == "1") }.toMutableMap()
        inputsMap to gates.map { it.split(" -> ", " ").let { Gate(it[0], it[1], it[2], it[3]) } }
    }
    run {
        println(part1(inputs, gates))
    }
    run {
        val maxZ = gates.map { it.c }.filter { it.startsWith("z") }.map { it.removePrefix("z") }.maxOf { it.toLong() }
        val notXorZ = gates.filter { it.c.first() == 'z' && it.c != "z$maxZ" && it.op != "XOR" }
        val xorNotXyz = gates.filter { it.a.first() !in "xy" && it.b.first() !in "xy" && it.c.first() != 'z' && it.op == "XOR" }

        fun firstZThatUsesC(c: String): String? {
            val gs = gates.filter { it.a == c || it.b == c }
            gs.find { it.c.startsWith('z') }?.let {
                return "z" + (it.c.drop(1).toInt() - 1).toString().padStart(2, '0')
            }
            return gs.firstNotNullOfOrNull { firstZThatUsesC(it.c) }
        }

        for (g in xorNotXyz) {
            val g2 = notXorZ.first { it.c == firstZThatUsesC(g.c) }
            val temp = g.c
            g.c = g2.c
            g2.c = temp
        }

        fun getWiresAsLong(registers: Map<String, Boolean>, type: Char) = registers.filter { it.key.startsWith(type) }.toList().sortedBy { it.first }.map { it.second }.joinToString("") { if (it) "1" else "0" }.reversed().toLong(2)

        val falseCarry = (getWiresAsLong(inputs, 'x') + getWiresAsLong(inputs, 'y') xor part1(inputs, gates)).countTrailingZeroBits().toString()
        val res2 = (notXorZ + xorNotXyz + gates.filter { it.a.endsWith(falseCarry) && it.b.endsWith(falseCarry) }).map { it.c }.sorted().joinToString(",")
        println(res2)
    }
}
