import kotlin.math.abs

data class Point(val x: Int, val y: Int)

fun main() {

    fun Point.getNeighbors(): List<Point> {
        val directions = listOf(-1, 0, 1)

        return directions
            .flatMap { dX -> directions.map { dY -> Point(x + dX, y + dY) } }
            .filter { it != this }
    }

    fun String.findNumberAt(point: Point, checked: MutableSet<Point>): Int {
        val firstIndex = generateSequence(point.x, Int::dec)
            .takeWhile { it >= 0 && this[it].isDigit() }
            .last()
        val lastIndex = generateSequence(point.x, Int::inc)
            .takeWhile { it < this.length && this[it].isDigit() }
            .last()
        val indices = firstIndex..lastIndex

        // Prevent duplicate numbers
        checked.addAll(indices.map { Point(it, point.y) })

        return indices
            .map(this::get)
            .joinToString("")
            .toInt()
    }

    fun findAdjacentNumbers(input: List<String>, point: Point, checked: MutableSet<Point>): List<Int> {
        return point.getNeighbors()
            .filter { p ->
                p.y in input.indices &&
                        p.x in input[p.y].indices &&
                        input[p.y][p.x].isDigit()
            }
            .mapNotNull { p ->
                if (checked.contains(p)) null else input[p.y].findNumberAt(p, checked)
            }
    }


    fun combined(
        input: List<String>,
        isSymbolValid: (Char) -> Boolean,
        formula: (List<Int>) -> Int): Int
    {
        val checked = mutableSetOf<Point>()

        return input.mapIndexed { y, line ->
            line
                .withIndex()
                .filter { isSymbolValid(it.value) }
                .map { findAdjacentNumbers(input, Point(it.index, y), checked) }
                .sumOf { formula(it) }
        }.sum()
    }

    fun Char.isSymbol(): Boolean {
        return !(this == '.' || this.isDigit())
    }


    fun part1(input: List<String>): Int {
        return combined(input, { it.isSymbol() }, List<Int>::sum)
    }

    fun part2(input: List<String>): Int {
        return combined(input, { c -> c == '*' }, { nums ->
            if (nums.size == 2) nums.reduce(Int::times) else 0
        })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}