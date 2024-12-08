package y2024

import readInput

fun main() {
    val input = readInput("Day08")
    val (n,m) = input.size to input[0].length
    val antennas = hashMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (i in input.indices) {
        for (j in input[i].indices) {
            val c = input[i][j]
            if (c != '.') {
                antennas.putIfAbsent(c, mutableListOf())
                antennas[c]!!.add(Pair(i, j))
            }
        }
    }
    run {
        val res1 = Array(n) {
            BooleanArray(m)
        }
        for (ant in antennas.values) {
            for (i in ant.indices) {
                for (j in ant.indices) {
                    if (i == j) continue
                    val (air, aic) = ant[i]
                    val (ajr, ajc) = ant[j]
                    val rr = air + 2*(ajr - air)
                    val rc = aic + 2*(ajc - aic)
                    if (rr in 0..<n && rc in 0..< m) {
                        res1[rr][rc] = true
                    }
                }
            }
        }
        println(res1.sumOf { it.count { it } })
    }
    run {
        val res2 = Array(n) {
            BooleanArray(m)
        }
        for (a in antennas.values) {
            for (i in a.indices) {
                for (j in a.indices) {
                    if (i == j) continue
                    val (air, aic) = a[i]
                    val (ajr, ajc) = a[j]
                    val dr = ajr - air
                    val dc = ajc - aic
                    for (k in 0 .. maxOf(n,m)) {
                        val rr = air + k * dr
                        val rc = aic + k * dc
                        if (rr in 0..<n && rc in 0..< m) {
                            res2[rr][rc] = true
                        } else break
                    }

                }
            }
        }
        println(res2.sumOf { it.count { it } })
    }
}
