fun main() {
    val input = readInput("Day03")
    var res1 = 0
    for (s in input) {
        val c1 = s.substring(0 until s.length/2)
        val c2 = s.substring(s.length/2)
        val err = c1.toSet().intersect(c2.toSet()).single()
        res1 += when (err) {
            in 'a'..'z' -> err - 'a' + 1
            else -> err - 'A' + 26 + 1
        }
    }
    println(res1)
    var res2 = 0
    for (i in input.indices step 3) {
        val s1 = input[i].toSet()
        val s2 = input[i+1].toSet()
        val s3 = input[i+2].toSet()
        val b = s1.intersect(s2).intersect(s3).single()
        res2 += when (b) {
            in 'a'..'z' -> b - 'a' + 1
            else -> b - 'A' + 26 + 1
        }
    }
    println(res2)
}
