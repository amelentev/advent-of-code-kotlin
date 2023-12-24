import java.io.File
import kotlin.math.abs

const val EPS = 1E-9
data class Point3(val x: Double, val y: Double, val z: Double)
operator fun Point3.plus(p: Point3) = Point3(x + p.x, y + p.y, z + p.z)
data class Line2(val a: Double, val b: Double, val c: Double)
typealias Hail = Pair<Point3, Point3>

fun main() {
    fun read(file: String) = readInput(file).map { line ->
        line.split(" @ ").map { p ->
            p.split(", ").map { it.toDouble() }.let { (x,y,z) ->
                Point3(x,y,z)
            }
        }.let {
            it[0] to it[1]
        }
    }

    fun solve1(file: String, range: ClosedFloatingPointRange<Double>): Int {
        val input = read(file)
        fun getLine(h: Pair<Point3, Point3>): Line2 {
            val (p1,d1) = h
            val p2 = p1 + d1
            if (p1.x == p2.x) TODO()
            val m = (p2.y - p1.y) / (p2.x - p1.x)
            return Line2(m, -1.0, p1.y - m*p1.x)
        }
        fun intersect(h1: Hail, h2: Hail): Point3? {
            val (a1,b1,c1) = getLine(h1)
            val (a2,b2,c2) = getLine(h2)
            val d = a1 * b2 - a2*b1
            if (abs(d) < EPS) return null
            return Point3((b1*c2 - b2*c1)/d, (c1*a2 - c2*a1)/d, 0.0)
        }
        var res = 0
        for (i1 in input.indices) {
            for (i2 in i1+1 .. input.lastIndex) {
                val (h1, h2) = input[i1] to input[i2]
                val p = intersect(h1, h2)
                if (p != null) {
                    val t1 = if (h1.second.x != 0.0) (p.x - h1.first.x) / h1.second.x
                        else (p.y - h1.first.y) / h1.second.y
                    val t2 = if (h2.second.x != 0.0) (p.x - h2.first.x) / h2.second.x
                        else (p.y - h2.first.y) / h2.second.y
                    if (p.x in range && p.y in range && t1 >= 0 && t2 >= 0) {
                        res++
                    }
                }
            }
        }
        return res
    }
    // generate smt file for z3
    fun solve2(file: String) {
        val input = read(file)
        val smt = File("$file.smt").printWriter()
        smt.println("""
            (declare-const rx Int)
            (declare-const ry Int)
            (declare-const rz Int)
            (declare-const vx Int)
            (declare-const vy Int)
            (declare-const vz Int)
        """.trimIndent())
        for (i in input.indices) {
            val (px,py,pz) = input[i].first
            val (vx,vy,vz) = input[i].second
            smt.println("(declare-const t$i Int)")
            fun add(c: Char, p: Double, v: Double) {
                val left = "(+ ${p.toLong()} (* ${v.toLong()} t$i))"
                val right = "(+ r$c (* v$c t$i))"
                smt.println("(assert (= $left $right))")
            }
            add('x', px, vx)
            add('y', py, vy)
            add('z', pz, vz)
            //println("$px ${if (sx>=0) "+" else ""} $sx * t$i = rx + vx * t$i")
            //println("$py ${if (sy>=0) "+" else ""} $sy * t$i = ry + vy * t$i")
            //println("$pz ${if (sz>=0) "+" else ""} $sz * t$i = rz + vz * t$i")
        }
        smt.println("(check-sat)")
        smt.println("(get-value (rx ry rz))")
        smt.close()
        println("z3 $file.smt") // or https://microsoft.github.io/z3guide/playground/Freeform%20Editing
    }
    assert(solve1("Day24t", 7.0..27.0) == 2)
    println(solve1("Day24", 200000000000000.0..400000000000000.0))
    solve2("Day24t")
    solve2("Day24")
}
