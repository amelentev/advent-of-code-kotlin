package y2024

import readInput
import java.util.PriorityQueue
import kotlin.math.sign

fun main() {
    val map = readInput("Day16")
    fun find(c: Char) = map.mapIndexedNotNull { i, line ->
        line.indexOf(c).takeIf { it >= 0 }?.let { j ->
            i to j
        }
    }.single()
    val start = find('S')
    val end = find('E')
    val dirs = listOf(
        0 to 1,
        1 to 0,
        0 to -1,
        -1 to 0
    )
    data class State(
        val r: Int,
        val c: Int,
        val dir: Int,
        val score: Long,
    ): Comparable<State> {
        override fun compareTo(other: State): Int {
            return (score - other.score).sign
        }
    }
    run {
        val visited = Array(map.size) {
            Array(map[0].length) {
                LongArray(4) { Long.MAX_VALUE }
            }
        }
        val prevs = Array(map.size) {
            Array(map[0].length) {
                Array(4) {
                    mutableListOf<State>()
                }
            }
        }
        val queue = PriorityQueue<State>()
        fun go(state: State, prev: State?) {
            val (r,c,dir,score) = state
            if (map[r][c] !in listOf('.', 'E', 'S')) return
            if (prev != null && visited[r][c][dir] == score) {
                prevs[r][c][dir].add(prev)
            }
            if (visited[r][c][dir] > score) {
                visited[r][c][dir] = score
                prevs[r][c][dir].clear()
                if (prev != null) prevs[r][c][dir].add(prev)
                queue.add(state)
            }
        }
        start.let { (r,c) ->
            go(State(r, c, 0, 0), null)
        }
        while (queue.isNotEmpty()) {
            val cur = queue.remove()
            val (r,c,dir,score) = cur
            val (dr,dc) = dirs[dir]
            val (r1,c1) = r + dr to c + dc
            go(State(r1, c1, dir, score + 1), cur)
            go(State(r, c, (dir+1)%4, score + 1000), cur)
            go(State(r, c, (dir+3)%4, score + 1000), cur)
        }
        val res1 = end.let { (r,c) ->
            visited[r][c].min()
        }
        println(res1)
        val res2 = mutableListOf<State>()
        val res2queue = ArrayDeque<State>()
        fun addRes2(state: State) {
            res2.add(state)
            res2queue.add(state)
        }
        end.let { (r,c) ->
            for (dir in dirs.indices) {
                if (visited[r][c][dir] == res1) addRes2(State(r,c,dir,res1))
            }
        }
        while (res2queue.isNotEmpty()) {
            val (r,c,dir) = res2queue.removeFirst()
            for (prev in prevs[r][c][dir]) {
                addRes2(prev)
            }
        }
        println(res2.map { (r,c) ->
            r to c
        }.distinct().size)
    }
}
