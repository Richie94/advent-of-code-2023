fun main() {
    data class MyMap(val destRange: Long, val fromRange: Long, val rangeLength: Long)
    data class GameInput(
        val seeds: List<Long>,
        val seedToSoil: List<MyMap>,
        val soilToFert: List<MyMap>,
        val fertToWater: List<MyMap>,
        val waterToLight: List<MyMap>,
        val lightToTemp: List<MyMap>,
        val tempToHumid: List<MyMap>,
        val humidToLoc: List<MyMap>,
    )


    fun splitMap(input: String) = input.split("\n").drop(1).map { line ->
        val (from, to, cost) = line.split(" ").mapNotNull { it.toLongOrNull() }
        MyMap(from, to, cost)
    }

    fun parseInput(input: List<String>): GameInput {
        val combined = input.joinToString("\n")
        val splits = combined.split("\n\n")
        val seeds = splits[0].split(":").last().split(" ").mapNotNull { it.toLongOrNull() }
        val seedToSoil = splitMap(splits[1])
        val soilToFert = splitMap(splits[2])
        val fertToWater = splitMap(splits[3])
        val waterToLight = splitMap(splits[4])
        val lightToTemp = splitMap(splits[5])
        val tempToHumid = splitMap(splits[6])
        val humidToLoc = splitMap(splits[7])
        return GameInput(seeds, seedToSoil, soilToFert, fertToWater, waterToLight, lightToTemp, tempToHumid, humidToLoc)
    }


    fun solveParsed(gameInput: GameInput): Long {
        return gameInput.seeds.map { seed ->
            // check if we hit one seedToSoil or map to ourself
            val soil = gameInput.seedToSoil.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= seed && seed <= fromRange + rangeLength
            }?.let { it.destRange + seed - it.fromRange } ?: seed
            val fert = gameInput.soilToFert.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= soil && soil <= fromRange + rangeLength
            }?.let { it.destRange + soil - it.fromRange } ?: soil
            val water = gameInput.fertToWater.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= fert && fert <= fromRange + rangeLength
            }?.let { it.destRange + fert - it.fromRange } ?: fert
            val light = gameInput.waterToLight.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= water && water <= fromRange + rangeLength
            }?.let { it.destRange + water - it.fromRange } ?: water
            val temp = gameInput.lightToTemp.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= light && light <= fromRange + rangeLength
            }?.let { it.destRange + light - it.fromRange } ?: light
            val humid = gameInput.tempToHumid.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= temp && temp <= fromRange + rangeLength
            }?.let { it.destRange + temp - it.fromRange } ?: temp
            val loc = gameInput.humidToLoc.firstOrNull { (_, fromRange, rangeLength) ->
                fromRange <= humid && humid <= fromRange + rangeLength
            }?.let { it.destRange + humid - it.fromRange } ?: humid
            println("$seed to $soil to $fert to $water to $light to $temp to $humid to $loc")
            loc
        }.min()
    }

    fun part1(input: List<String>): Long {
        val parsed = parseInput(input)
        return solveParsed(parsed)
    }

    fun part2(input: List<String>): Long {
        val parsed = parseInput(input)
        // adjust seeds
        val pairs = parsed.seeds.chunked(2)
        val seeds = mutableListOf<Long>()
        pairs.forEach { (from, amount) ->
            for (i in 0 until amount) {
                seeds.add(from + i)
            }
        }
        return solveParsed(parsed.copy(seeds = seeds))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println("P1")
    check(part1(testInput) == 35L)
    println("P2")
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
