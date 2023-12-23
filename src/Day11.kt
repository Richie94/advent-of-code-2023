import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class LongPoint(val x: Long, val y: Long)

class Day11 : Day("11", 374, null) {
    override fun part1(input: List<String>): Long {
        return solve(input, 2 - 1)
    }

    override fun part2(input: List<String>): Long {
        return solve(input, 1000000 - 1)
    }

    private fun solve(input: List<String>, galaxyAge : Int): Long {
        val emptyRows = input.indices
            .filter { input[it].all { c -> c == '.' } }
        val emptyCols = input[0].indices
            .filter { col -> input.indices.all { row -> input[row][col] == '.' } }
        val galaxies = input.indices
            .flatMap { row ->
                input[row].indices
                    .filter { col -> input[row][col] == '#' }
                    .map { col -> LongPoint(col.toLong() + emptyCols.count { it < col } * galaxyAge, row.toLong() + emptyRows.count { it < row } * galaxyAge) }
            }

        // for every pair of galaxies, calculate the distance between them
        // if empty row or col between them, this counts double
        return galaxies.indices.sumOf { galaxyIndex ->
            galaxies.indices.sumOf { otherIndex ->
                if (galaxyIndex < otherIndex) {
                    val galaxy = galaxies[galaxyIndex]
                    val other = galaxies[otherIndex]
                    val xMax = max(galaxy.x, other.x)
                    val yMax = max(galaxy.y, other.y)
                    val xMin = min(galaxy.x, other.x)
                    val yMin = min(galaxy.y, other.y)

                    (xMax - xMin) + (yMax - yMin)
                } else {
                    0
                }
            }
        }
    }
}

fun main() {
    Day11().solve()
}