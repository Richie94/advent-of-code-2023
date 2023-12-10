data class Game(val nodes: Map<String, Pair<String, String>>, val instructions: String)


val regex = Regex("(\\w+) = \\((\\w+), (\\w+)\\)")
fun parseGame(input: List<String>): Game {
    return Game(input.drop(2).associate {
        val (from, left, right) = regex.find(it)!!.groupValues.drop(1)
        from to (left to right)
    }, input.first())
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun main() {
    fun part1(input: List<String>): Int {
        val game = parseGame(input)
        var step = 0
        var current = "AAA"
        while (current != "ZZZ") {
            val instruction = if (step >= game.instructions.length) game.instructions[step % game.instructions.length] else game.instructions[step]
            if (instruction == 'L') {
                current = game.nodes[current]!!.first
            } else {
                current = game.nodes[current]!!.second
            }
            step++
        }
        return step
    }

    fun part2(input: List<String>): Long {
        val game = parseGame(input)
        // check for each node the LCM when it reaches a Z node
        return game.nodes.keys.filter { it.endsWith("A") }.map {
            var current = it
            var step = 0
            while (!current.endsWith("Z")) {
                val instruction = if (step >= game.instructions.length) game.instructions[step % game.instructions.length] else game.instructions[step]
                if (instruction == 'L') {
                    current = game.nodes[current]!!.first
                } else {
                    current = game.nodes[current]!!.second
                }
                step++
            }
            step.toLong()
        }.let {
            findLCMOfListOfNumbers(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput) == 6)
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
