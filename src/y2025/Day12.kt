package y2025

import readInput

fun main() {
    val input = readInput("Day12").fold(mutableListOf(mutableListOf<String>())) { acc, line ->
        if (line.isNotEmpty()) {
            acc.last().add(line)
        } else {
            acc.add(mutableListOf())
        }
        acc
    }
    val presents = input.dropLast(1).associate { present ->
        val id = present[0].removeSuffix(":").toInt()
        id to present.drop(1).sumOf { line  -> line.count { it == '#' } }
    }

    var res = 0
    for (tree in input.last()) {
        val (shape, presentsToPlace) = tree.split(": ")
        val (n,m) = shape.split("x").map { it.toInt() }
        val space = presentsToPlace.split(" ").map { it.toInt() }.withIndex().sumOf { (idx, c) ->
            c * presents[idx]!!
        }
        if (n*m >= space) res++
    }
    println(res)
}
