enum class Directions {
    NORTH, SOUTH, EAST, WEST
}

class Day10 : Day("10", null, 8) {

    override fun part1(input: List<String>): Long {
        val connections = mutableMapOf<Point, MutableList<Point>>()
        val visited = mutableSetOf<Point>()
        val maxY = input.size
        val maxX = input[0].length
        var start = Point(0, 0)
        input.withIndex().forEach { (y, line) ->
            line.withIndex().forEach { (x, c) ->
                val point = Point(x, y)
                val directions = when (c) {
                    '|' -> listOf(Directions.NORTH, Directions.SOUTH)
                    '-' -> listOf(Directions.EAST, Directions.WEST)
                    'L' -> listOf(Directions.NORTH, Directions.EAST)
                    'J' -> listOf(Directions.NORTH, Directions.WEST)
                    '7' -> listOf(Directions.SOUTH, Directions.WEST)
                    'F' -> listOf(Directions.SOUTH, Directions.EAST)
                    '.' -> emptyList()
                    'S' -> {
                        start = point
                        emptyList()
                    }
                    else -> error("Unknown sign $c")
                }

                directions.forEach { direction ->
                    when (direction) {
                        Directions.NORTH -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (y - 1 >= 0) {
                                connections.getValue(point).add(Point(x, y - 1))
                            }
                        }

                        Directions.SOUTH -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (y + 1 < maxY) {
                                connections.getValue(point).add(Point(x, y + 1))
                            }
                        }

                        Directions.WEST -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (x - 1 >= 0) {
                                connections.getValue(point).add(Point(x - 1, y))
                            }
                        }

                        Directions.EAST -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (x + 1 < maxX) {
                                connections.getValue(point).add(Point(x + 1, y))
                            }
                        }
                    }
                }
            }
        }
        visited.add(start)
        var current = connections.filter { it.value.contains(start) }.keys
        var step = 0L
        while (current.isNotEmpty()) {
            val next = current.flatMap { connections[it]!! }.filter { !visited.contains(it) }.toSet()
            visited.addAll(next)
            current = next
            step++
        }
        return step
    }

    override fun part2(input: List<String>): Long {
        val connections = mutableMapOf<Point, MutableList<Point>>()
        val visited = mutableSetOf<Point>()
        val maxY = input.size
        val maxX = input[0].length
        var start = Point(0, 0)
        input.withIndex().forEach { (y, line) ->
            line.withIndex().forEach { (x, c) ->
                val point = Point(x, y)
                val directions = when (c) {
                    '|' -> listOf(Directions.NORTH, Directions.SOUTH)
                    '-' -> listOf(Directions.EAST, Directions.WEST)
                    'L' -> listOf(Directions.NORTH, Directions.EAST)
                    'J' -> listOf(Directions.NORTH, Directions.WEST)
                    '7' -> listOf(Directions.SOUTH, Directions.WEST)
                    'F' -> listOf(Directions.SOUTH, Directions.EAST)
                    '.' -> emptyList()
                    'S' -> {
                        start = point
                        emptyList()
                    }
                    else -> error("Unknown sign $c")
                }

                directions.forEach { direction ->
                    when (direction) {
                        Directions.NORTH -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (y - 1 >= 0) {
                                connections.getValue(point).add(Point(x, y - 1))
                            }
                        }

                        Directions.SOUTH -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (y + 1 < maxY) {
                                connections.getValue(point).add(Point(x, y + 1))
                            }
                        }

                        Directions.WEST -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (x - 1 >= 0) {
                                connections.getValue(point).add(Point(x - 1, y))
                            }
                        }

                        Directions.EAST -> {
                            connections.putIfAbsent(point, mutableListOf())
                            if (x + 1 < maxX) {
                                connections.getValue(point).add(Point(x + 1, y))
                            }
                        }
                    }
                }
            }
        }
        visited.add(start)
        var current = connections.filter { it.value.contains(start) }.keys
        while (current.isNotEmpty()) {
            val next = current.flatMap { connections[it]!! }.filter { !visited.contains(it) }.toSet()
            visited.addAll(next)
            current = next
        }

        var count = 0L
        input.withIndex().forEach { (y, line) ->
            var pipeCount = 0
            line.withIndex().forEach { (x, c) ->
                val test = Point(x, y)
                if (c == '.' && pipeCount % 2 == 1 ) {
                    count++
                    print("I")
                } else if (test in visited && c == '|') {
                    pipeCount++
                    print(c)
                } else {
                    print(c)
                }
            }
            println()
        }

        return count
    }
}

fun main() {
    Day10().solve()
}