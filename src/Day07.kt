sealed class Node(val size: Long) {
    class Dir(val children: MutableMap<String, Node> = mutableMapOf(), size: Long = 0) : Node(size)
    class File(size: Long) : Node(size)
}

fun main() {
    val input = readInput("Day07")
    val root = Node.Dir()
    val stack = ArrayDeque(listOf(root))
    for (s in input) {
        when {
            s.startsWith("$ cd ") -> {
                when (val dir = s.removePrefix("$ cd ")) {
                    "/" -> {
                        stack.clear()
                        stack.addLast(root)
                    }
                    ".." -> stack.removeLast()
                    else -> stack.addLast(stack.last().children[dir] as Node.Dir)
                }
            }
            s.startsWith("$ ls") -> continue
            else -> {
                val (size, name) = s.split(' ')
                if (size == "dir") {
                    stack.last().children[name] = Node.Dir()
                } else {
                    stack.last().children[name] = Node.File(size.toLong())
                }
            }
        }
    }
    val dirs = mutableListOf<Node.Dir>()
    fun calcSize(n: Node): Node = when (n) {
        is Node.Dir -> {
            val children = n.children.mapValues { calcSize(it.value) }
            val size = children.values.sumOf { it.size }
            Node.Dir(children.toMutableMap(), size).also {
                dirs.add(it)
            }
        }
        is Node.File -> n
    }
    val newRoot = calcSize(root)
    println(dirs.filter { it.size <= 100_000 }.sumOf { it.size })
    val freeSpace = 70000000L - newRoot.size
    val spaceNeed = 30000000
    println(dirs.map { it.size }.filter { freeSpace + it >= spaceNeed }.min())
}
