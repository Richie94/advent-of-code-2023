fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> line.split(" ").mapNotNull { it.toIntOrNull() } }
            .let { (t, d) -> t.zip(d) }
            .map { (time, distance) -> (0..time).map { (time - it) * it }.count { it > distance } }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val (time, distance) = input.map { it.substringAfter(" ").replace(" ", "").toLong() }
        return (0..time).map { (time - it) * it }.count { it > distance }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
