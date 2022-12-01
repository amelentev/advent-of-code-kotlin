fun main() {
    val input = readInput("Day01")
    var cur = 0L
    val list = mutableListOf<Long>()
    for (s in input) {
        if (s == "") {
            list.add(cur)
            cur = 0
        } else {
            cur += s.toLong()
        }
    }
    list.sort()
    println(list.last())
    println(list.takeLast(3).sum())
}
