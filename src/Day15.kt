fun main() {
    fun hash(s: String) = s.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }
    val input = readInput("Day15")[0].split(",")
    println(input.sumOf { hash(it) })

    val boxes = Array(256) { mutableMapOf<String, Long>() }
    for (command in input) {
        if (command.endsWith('-')) {
            val id = command.substringBefore('-')
            boxes[hash(id)].remove(id)
        } else {
            val id = command.substringBefore('=')
            val f = command.substringAfter('=').toLong()
            boxes[hash(id)][id] = f
        }
    }
    var res2 = 0L
    for (bi in boxes.indices) {
        res2 += boxes[bi].values.mapIndexed { li, l ->
            (bi+1) * (li+1) * l
        }.sum()
    }
    println(res2)
}