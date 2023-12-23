class Day12 : Day("12", 21, 525152) {
    override fun part1(input: List<String>): Long {
        return solve(input, 1)
    }

    override fun part2(input: List<String>): Long {
        return solve(input, 3)
    }

    private fun solve(input: List<String>, multiplier: Int): Long {
        return input.sumOf { line ->
            val springs = List(multiplier) { line.split(" ").first().toList() }.flatten()
            val groups = List(multiplier) { line.split(" ").last().split(",").map { it.toInt() } }.flatten()

            val maxSpring = groups.sum()

            // naive approach: generate all possible combinations of springs and check if groups match
            val allSprings = getCombination("", springs, maxSpring)
            allSprings.count { springCombination ->
                springCombination.split(".").map { it.count { char -> char == '#' } }.filter { it > 0 } == groups
            }
        }.toLong()
    }

    private fun getCombination(prefix: String, chars: List<Char>, maxSpring: Int): Collection<String> {
        if (chars.isEmpty()) return listOf(prefix)
        if (prefix.count { it == '#' } > maxSpring) return listOf(prefix)
        val firstChar = chars.first()
        return when (firstChar) {
            '.' -> {
                getCombination("$prefix.", chars.drop(1), maxSpring)
            }
            '#' -> {
                getCombination("$prefix#", chars.drop(1), maxSpring)
            }
            else -> {
                getCombination("$prefix.", chars.drop(1), maxSpring) + getCombination("$prefix#", chars.drop(1), maxSpring)
            }
        }
    }
}

fun main() {
    Day12().solve()
}