package y2024

import readInput

fun main() {
    val input = readInput("Day09")[0].toList().map { it - '0' }.let {
        if (it.size % 2 == 1) it + listOf(0)
        else it
    }
    run {
        val map = mutableListOf<Int>()

        for (i in input.indices step 2) {
            repeat(input[i]) {
                map.add(1 + (i/2))
            }
            repeat(input[i+1]) {
                map.add(0)
            }
        }
        var right = map.lastIndex
        var left = 0
        while (left < right) {
            if (map[right] == 0) {
                right--
                continue
            }
            if (map[left] == 0) {
                map[left] = map[right]
                map[right] = 0
                right --
            }
            left++
        }
        val res1 = map.withIndex().sumOf {
            it.index * maxOf(it.value - 1L, 0L)
        }
        println(res1)
    }
    data class Space(var start: Int, var size: Int, val fileId: Int)
    run {
        var block = 0
        var fileId = 0
        val files = input.mapIndexed { index, v ->
            if (index % 2 == 0) {
                Space(block, v, ++fileId)
            } else {
                Space(block, v, 0)
            }.also {
                block += v
            }
        }
        for (i in files.indices.reversed()) {
            if (files[i].fileId == 0) continue
            for (j in 0 until i) {
                if (files[j].fileId != 0 || files[j].size < files[i].size) continue
                files[i].start = files[j].start
                files[j].start += files[i].size
                files[j].size -= files[i].size
                break
            }
        }
        val res2 = files.sumOf {
            maxOf(it.fileId-1L, 0L) * (it.start + it.start+it.size-1L)*it.size/2L
        }
        println(res2)
    }
}
