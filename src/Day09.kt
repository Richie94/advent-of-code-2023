
class Day09 : Day("09", 114L, 2) {
    override fun part1(input: List<String>): Long {
        return solveDay(input, true)
    }

    override fun part2(input: List<String>): Long {
        return solveDay(input, false)
    }

    private fun solveDay(input: List<String>, extrapolateTail: Boolean) : Long {
        return input.sumOf { line ->
            val numbers = line.split(" ").map { it.toInt() }
            val history = mutableListOf(numbers)
            var current = numbers
            while (!current.all { it == 0 }) {
                val next = current.zipWithNext()
                    .map { (a, b) -> b - a }
                history.add(next)
                current = next
            }
            val reversedHistory = history.reversed().map { it.toMutableList() }
            val gap = reversedHistory.indices.map {
                val extrapolated = if (it == 0) {
                    0
                } else if (!extrapolateTail) {
                    reversedHistory[it].first() - reversedHistory[it - 1].first()
                } else {
                    reversedHistory[it].last() + reversedHistory[it - 1].last()
                }

                if (extrapolateTail) {
                    reversedHistory[it].add(extrapolated)
                } else {
                    reversedHistory[it].add(0, extrapolated)
                }
                extrapolated
            }
            gap.last()
        }.toLong()
    }

}
fun main() {
    Day09().solve()
}