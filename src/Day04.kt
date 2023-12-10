import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val (left, right) = line.split(": ").last().split("|")
            val leftNumbers = left.split(" ").mapNotNull { it.toIntOrNull() }
            val rightNumbers = right.split(" ").mapNotNull { it.toIntOrNull() }
            val matchingNumbers = leftNumbers.filter { it in rightNumbers }.count()
            if (matchingNumbers == 0) {
                0
            } else {
                2.0.pow((matchingNumbers - 1).toDouble())
            }
        }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val wonCards = mutableMapOf<Int, Int>()
        val totalCardAmount = input.size
        input.forEach { line ->
            val cardNumber = line.split(": ").first().split(" ").last().toInt()
            val cardAmount = (wonCards[cardNumber] ?: 0) + 1

            val (left, right) = line.split(": ").last().split("|")
            val leftNumbers = left.split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val rightNumbers = right.split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val matches = leftNumbers.count { it in rightNumbers }
            for (i in cardNumber + 1..cardNumber + matches) {
                if (i < totalCardAmount) {
                    wonCards[i] = (wonCards[i] ?: 0) + cardAmount
                }
            }
            // add own number
            wonCards[cardNumber] = (wonCards[cardNumber] ?: 0) + 1

        }
        println("won: " + wonCards[1].toString() + " "+ wonCards[2] + " " + wonCards[3])
        return wonCards.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
