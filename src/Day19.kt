fun main() {
    data class Blueprint(
        val id: Int,
        val oreRobotCostOre: Int,
        val clayRobotCostOre: Int,
        val obsidianRobotCost: Pair<Int, Int>,
        val geodeRobotCost: Pair<Int, Int>,
    )
    val blueprints = readInput("Day19").mapIndexed { index, line ->
        val parts = line.split("Blueprint ", ": Each ore robot costs ", ". Each clay robot costs ", ". Each obsidian robot costs ", ". Each geode robot costs ").filter { it.isNotEmpty() }
        assert(parts.size == 5) { parts }
        assert(parts[0].toInt() == index + 1) { parts[0] }
        fun parseCost2(s: String) = s.split(" ore and ", " clay", " obsidian.").filter { it.isNotEmpty() }.map { it.toInt() }.let { it[0] to it[1] }
        Blueprint(
            index+1,
            parts[1].removeSuffix(" ore").toInt(),
            parts[2].removeSuffix(" ore").toInt(),
            parseCost2(parts[3]),
            parseCost2(parts[4]),
        )
    }
    fun solve(b: Blueprint, timeLimit: Int): Int {
        data class State(
            val res: IntArray,
            val robots: IntArray
        ) {
            fun pass1min() = State(res1min(), robots)
            fun res1min() = intArrayOf(res[0] + robots[0], res[1] + robots[1], res[2] + robots[2], res[3] + robots[3])
            fun incRobots(ir: Int) = robots.clone().also { it[ir] ++ }

            fun geodesMin(t: Int) = res[3] + t * robots[3]
            fun geodesMax(t: Int) = res[3] + t*(robots[3] + robots[3]+t-1)/2

            fun buyRobots(): List<State> {
                val states = mutableListOf<State>()
                if (res[0] >= b.oreRobotCostOre) {
                    val res1 = res1min()
                    res1[0] -= b.oreRobotCostOre
                    states.add(State(res1, incRobots(0)))
                }
                if (res[0] >= b.clayRobotCostOre) {
                    val res1 = res1min()
                    res1[0] -= b.clayRobotCostOre
                    states.add(State(res1, incRobots(1)))
                }
                if (res[0] >= b.obsidianRobotCost.first && res[1] >= b.obsidianRobotCost.second) {
                    val res1 = res1min()
                    res1[0] -= b.obsidianRobotCost.first
                    res1[1] -= b.obsidianRobotCost.second
                    states.add(State(res1, incRobots(2)))
                }
                if (res[0] >= b.geodeRobotCost.first && res[2] >= b.geodeRobotCost.second) {
                    val res1 = res1min()
                    res1[0] -= b.geodeRobotCost.first
                    res1[2] -= b.geodeRobotCost.second
                    states.add(State(res1, incRobots(3)))
                }
                return states
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as State

                if (!res.contentEquals(other.res)) return false
                if (!robots.contentEquals(other.robots)) return false

                return true
            }

            override fun hashCode(): Int {
                var result = res.contentHashCode()
                result = 31 * result + robots.contentHashCode()
                return result
            }
        }
        val states = HashMap<State, Int>()
        val queue = ArrayDeque<State>()
        State(intArrayOf(0,0,0,0), intArrayOf(1,0,0,0)).let {
            states[it] = 0
            queue.add(it)
        }
        var maxGeodes = 0
        var maxTime = 0
        while (queue.isNotEmpty()) {
            val s0 = queue.removeFirst()
            val time = states[s0]!!
            maxGeodes = maxOf(maxGeodes, s0.geodesMin(timeLimit - time))
            if (time > maxTime) {
                maxTime = time
                print(".$maxTime")
            }
            if (time >= timeLimit) continue
            if (s0.geodesMax(timeLimit - time) <= maxGeodes) continue
            val s1 = s0.pass1min()
            if (states.putIfAbsent(s1, time+1) == null) {
                queue.add(s1)
            }
            for (s2 in s0.buyRobots()) {
                if (states.putIfAbsent(s2, time+1) == null) {
                    queue.add(s2)
                }
            }
        }
        return maxGeodes
    }
    val res1 = blueprints.sumOf {
        println("Blueprint #${it.id}")
        val geodes = solve(it, 24)
        println("\ngeodes=${geodes}")
        it.id * geodes
    }
    println("\nPart1:")
    println(res1)

    val res2 = blueprints.take(3).map {
        println("Blueprint #${it.id}")
        val geodes = solve(it, 32)
        println("\ngeodes=${geodes}")
        geodes
    }
    println("Part2:")
    println(res2[0] * res2[1] * res2[2])
}
