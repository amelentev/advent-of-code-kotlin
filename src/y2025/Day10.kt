package y2025

import io.ksmt.KContext
import io.ksmt.solver.z3.KZ3Solver
import readInput

fun main() {
    val input = readInput("Day10").map { line ->
        val lights = line.substringBefore("]").removePrefix("[")
        val buttons = line.substringBefore("{").substringAfter("]").trim().split(" ")
            .map { b ->
                b.trim('(', ')').split(",")
                    .map { it.toInt() }
                    // .sumOf { 1 shl it.toInt() }
            }
        val jolts = line.substringAfter("{").removeSuffix("}").split(",").map { it.toInt() }
        Triple(lights, buttons, jolts)
    }
    fun solve1(lights: String, buttons: List<List<Int>>): Int {
        val n = lights.length
        val k = buttons.size
        val rl = BooleanArray(n)
        var res = Int.MAX_VALUE
        for (s in 0 until (1 shl k)) {
            rl.fill(false)
            for (j in 0 until k) {
                if (s and (1 shl j) != 0) {
                    for (b in buttons[j]) {
                        rl[b] = !rl[b]
                    }
                }
            }
            if (lights.indices.all { (lights[it] == '#') == rl[it] }) {
                res = minOf(res, Integer.bitCount(s))
            }
        }
        return res
    }
    val res1 = input.sumOf {
        solve1(it.first, it.second)
    }
    println(res1)

    // too slow
    fun solve2(buttons: List<List<Int>>, jolts: List<Int>): Int {
        val n = jolts.size
        val k = buttons.size
        val cur = IntArray(n)
        var res = Int.MAX_VALUE
        var depth = 0
        fun step(j0: Int) {
            if (depth >= res) return
            if (cur.indices.all { cur[it] == jolts[it] }) {
                res = minOf(res, depth)
            }
            if (cur.indices.any { cur[it] > jolts[it] }) {
                return
            }
            for (j in j0 until k) {
                depth ++
                for (b in buttons[j]) {
                    cur[b]++
                }
                step(j)
                for (b in buttons[j]) {
                    cur[b]--
                }
                depth --
            }
        }
        step(0)
        return res
    }

    fun omt(buttons: List<List<Int>>, jolts: List<Int>): Int {
        val ctx = KContext()
        with(ctx) {
            val bs = buttons.indices.map { mkConst("b$it", intSort) }
            MinimizingSolverZ3(KZ3Solver(this)).use { solver ->
                for (b in bs) {
                    solver.assert(b ge 0.expr)
                }
                for (j in jolts.indices) {
                    solver.assert(
                        mkArithAdd(
                            buttons.indices.filter {
                                buttons[it].contains(j)
                            }.map {
                                bs[it]
                            }
                        ) eq jolts[j].expr
                    )
                }
                val bSum = mkArithAdd(bs)
                val model = solver.modelMinimizing(bSum)
                return model.eval(bSum).stringRepr.toInt()
            }
        }
    }

    val res2 = input.sumOf {
        val r = omt(it.second, it.third)
        println(r)
        r
    }
    println(res2)
}
