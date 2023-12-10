fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val digits = it.filter { it.isDigit() }
            digits.first().toString() + digits.last().toString()
        }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val replacements = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
            "zero" to 0
        )
        return part1(input.map {
            var result = it
            // iterate through string and replace all occurrences of the keys with the values
            var index = 0
            while (index < result.length) {
                replacements.forEach { (key, value) ->
                    if (result.subSequence(index, result.length).startsWith(key)) {
                        // replace does not seem to work, as eightwo should be replaced with 82 it seems
                        result = result.substring(0, index) + value + result.substring(index , result.length)
                        index++
                    }
                }
                index++
            }
            result
        })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
