package y2024

import readInput
import java.math.BigInteger

fun main() {
    val input = readInput("Day17")
    val b8 = BigInteger.valueOf(8L)
    fun exec(prog: List<Int>, regs: List<BigInteger>): List<Int> {
        val res = mutableListOf<Int>()
        var (regA, regB, regC) = regs
        var ip = 0
        while (ip < prog.size) {
            val opcode = prog[ip]
            val operand = prog[ip+1]
            fun getCombo(): BigInteger {
                if (operand <= 3) return operand.toBigInteger()
                return when (operand) {
                    4 -> regA
                    5 -> regB
                    6 -> regC
                    else -> error("Invalid combo operand $operand")
                }
            }
            fun dv() = regA / BigInteger.TWO.pow(getCombo().intValueExact())
            when (opcode) {
                0 -> { // adv
                    regA = dv()
                }
                1 -> { // bxl
                    regB = regB.xor(BigInteger.valueOf(operand.toLong()))
                }
                2 -> { // bst
                    regB = getCombo().mod(b8)
                }
                3 -> { // jnz
                    if (regA != BigInteger.ZERO) {
                        ip = operand
                        continue
                    }
                }
                4 -> { // bxc
                    regB = regB.xor(regC)
                }
                5 -> { // out
                    res.add(getCombo().mod(b8).intValueExact())
                }
                6 -> { // bdv
                    regB = dv()
                }
                7 -> { // cdv
                    regC = dv()
                }
                else -> error("Invalid opcode $opcode")
            }
            ip+=2
        }
        return res
    }
    val regA = input[0].removePrefix("Register A: ").toBigInteger()
    val regB = input[1].removePrefix("Register B: ").toBigInteger()
    val regC = input[2].removePrefix("Register C: ").toBigInteger()
    val prog = input[4].removePrefix("Program: ").split(",").map { it.toInt() }

    run {
        val res1 = exec(prog, listOf(regA, regB, regC))
        println(res1.joinToString(","))
    }
    run {
        //val minA = BigInteger.valueOf(8).pow(15).longValueExact() + 2
        val minA = 47901490064274L
        var prevRegA = 0L
        var gcdDiff: BigInteger? = null
        for (newRegA in minA until Long.MAX_VALUE/2 step 32768) {
            val res = exec(prog, listOf(newRegA.toBigInteger(), regB, regC))
            val wrongIdx = prog.indices.find { it >= res.size || prog[it] != res[it] }
            if (wrongIdx == null) {
                println("$newRegA \t FOUND!")
                break
            }
            if (wrongIdx >= 11) {
                if (prevRegA != 0L) {
                    val diff = newRegA - prevRegA
                    gcdDiff = if (gcdDiff == null) diff.toBigInteger()
                    else gcdDiff.gcd(diff.toBigInteger())
                    println("$newRegA \t $diff \t $gcdDiff \t ${newRegA % 8} \t ${res.size} $wrongIdx")
                }
                prevRegA = newRegA
            }
        }
    }
}
