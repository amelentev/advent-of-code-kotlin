fun main() {
    fun read() = readInput("Day14").map { it.toCharArray() }
    var map: List<CharArray> = read()
    fun tiltN() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') {
                    map[i][j] = '.'
                    var i1 = i
                    while (i1 > 0 && map[i1-1][j] == '.') i1--
                    map[i1][j] = 'O'
                }
            }
        }
    }
    fun tiltS() {
        for (i in map.indices.reversed()) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') {
                    map[i][j] = '.'
                    var i1 = i
                    while (i1+1 < map.size  && map[i1+1][j] == '.') i1++
                    map[i1][j] = 'O'
                }
            }
        }
    }
    fun tiltW() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') {
                    map[i][j] = '.'
                    var j1 = j
                    while (j1-1 >= 0 && map[i][j1-1] == '.') j1--
                    map[i][j1] = 'O'
                }
            }
        }
    }
    fun tiltE() {
        for (i in map.indices) {
            for (j in map[i].indices.reversed()) {
                if (map[i][j] == 'O') {
                    map[i][j] = '.'
                    var j1 = j
                    while (j1+1 < map[i].size && map[i][j1+1] == '.') j1++
                    map[i][j1] = 'O'
                }
            }
        }
    }
    run {
        tiltN()
        println(map.joinToString("\n") { String(it) })
        var res1 = 0
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') res1 += map.size-i
            }
        }
        println(res1)
    }
    run {
        map = read()
        val prev = HashMap<String, Int>()
        prev[map.joinToString("\n") { String(it) }] = 0
        var step = 1
        while (step <= 1000000000) {
            tiltN()
            tiltW()
            tiltS()
            tiltE()
            step++
            val cur = map.joinToString("\n") { String(it) }
            val previ = prev[cur]
            if (previ != null) {
                val len = step - previ
                val need = (1000000000 - step)
                step += (need / len) * len
            } else {
                prev[cur] = step
            }
        }
        var res1 = 0
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') res1 += map.size-i
            }
        }
        println(res1)
    }
}