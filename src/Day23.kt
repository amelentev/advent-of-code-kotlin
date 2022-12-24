fun main() {
    data class Pos(
        val x: Int,
        val y: Int
    ) {
        fun nw() = Pos(x-1, y-1)
        fun n() = Pos(x, y-1)
        fun ne() = Pos(x+1, y-1)
        fun e() = Pos(x+1, y)
        fun se() = Pos(x+1, y+1)
        fun s() = Pos(x, y+1)
        fun sw() = Pos(x-1, y+1)
        fun w() = Pos(x-1, y)

        fun all() = listOf(nw(), n(), ne(), e(), se(), s(), sw(), w())

        fun northSide() = listOf(nw(), n(), ne())
        fun southSide() = listOf(sw(), s(), se())
        fun westSide() = listOf(nw(), w(), sw())
        fun eastSide() = listOf(ne(), e(), se())

        fun sides() = listOf(northSide(), southSide(), westSide(), eastSide())
    }
    val map = readInput("Day23")
    var elves = HashSet<Pos>()
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (map[y][x] == '#') {
                elves.add(Pos(x,y))
            }
        }
    }
    fun printMap() {
        for (y in elves.minOf { it.y } .. elves.maxOf { it.y }) {
            for (x in elves.minOf { it.x } .. elves.maxOf { it.x })
                print(if (Pos(x, y) in elves) "#" else ".")
            println()
        }
        println()
    }
    fun newPos(e: Pos, time: Int): Pos {
        if (e.all().all { it !in elves }) return e
        for (t in time until time+4) {
            val side = e.sides()[t % 4]
            if (side.all { it !in elves }) return side[1]
        }
        return e
    }
    repeat(10) { time ->
        val newSet = elves.groupBy { newPos(it, time) }
        elves = elves.map { e ->
            newPos(e, time).takeIf { newSet[it]!!.size == 1 } ?: e
        }.toHashSet()
        //printMap()
    }
    var res1 = 0
    for (x in elves.minOf { it.x } .. elves.maxOf { it.x }) {
        for (y in elves.minOf { it.y } .. elves.maxOf { it.y }) {
            if (Pos(x,y) !in elves) res1++
        }
    }
    println(res1)
    for (time in 10 until 10000) {
        val newSet = elves.groupBy { newPos(it, time) }
        val elves1 = elves.map { e ->
            newPos(e, time).takeIf { newSet[it]!!.size == 1 } ?: e
        }.toHashSet()
        if (elves == elves1) {
            println(time+1)
            break
        }
        elves = elves1
    }
}
