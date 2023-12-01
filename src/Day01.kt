fun main() {
    val input = readInput("Day01")
    var res = 0L
    for (line in input) {
        val a = line.find { it.isDigit() }.toString()
        val b = line.findLast { it.isDigit() }.toString()
        res += (a+b).toLong()
    }
    println(res)
    res = 0L
    val digits = listOf(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    ) + (0..9).map { it.toString() }
    for (line in input) {
        val a = line.findAnyOf(digits)!!.second
        val b = line.findLastAnyOf(digits)!!.second
        res += (a.toIntOrNull() ?: (digits.indexOf(a)+1))*10 + (b.toIntOrNull() ?: (digits.indexOf(b)+1))
    }
    println(res)
}