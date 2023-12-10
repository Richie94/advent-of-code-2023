fun main() {

    val regexLine = Regex("Game (\\d+): (.*)")
    val regexGame = Regex("(\\d+) (\\w+)")

    fun parseGame(line: String): Pair<Int, List<List<Pair<Int, String>>>> {
        val find = regexLine.find(line)!!
        val gameId = find.groupValues[1].toInt()
        val games = find.groupValues[2].split(";")
            .map { it.trim() }
            .map { game ->
                game.split(",").map {
                    val groups = regexGame.find(it)!!.groupValues
                    groups[1].toInt() to groups[2]
                }
            }
        return gameId to games
    }

    fun part1(input: List<String>): Int {
        val cubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return input.mapNotNull { line ->
            val (gameId, games) = parseGame(line)
            for (game in games) {
                val impossibleMove = game.filter { (amount, color) ->
                    cubes.getValue(color) < amount
                }

                if (impossibleMove.isNotEmpty()) {
                    return@mapNotNull null
                }
            }
            gameId
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (_, games) = parseGame(line)
            val minimumMap = mutableMapOf<String, Int>()
            games.flatten().forEach {
                val (amount, color) = it
                minimumMap.putIfAbsent(color, 0)
                if (minimumMap.getValue(color) < amount) {
                    minimumMap[color] = amount
                }
            }
            minimumMap.values.reduce { acc, i -> acc * i }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
