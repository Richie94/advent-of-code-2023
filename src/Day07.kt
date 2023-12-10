data class Card(val value: String, val part1: Boolean = true) : Comparable<Card> {

    // comparing with poker rules
    override fun compareTo(other: Card): Int {
        val order = if (part1) order1 else order2
        return order.indexOf(other.value).compareTo(order.indexOf(value))
    }

    companion object {
        private val order1 = listOf("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A")
        private val order2 = listOf("J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A")
    }
}

enum class Combi {
    HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
}

data class Hand(val combination: List<Pair<Card, Int>>, val bid: Int, val origString: String, val part1: Boolean) : Comparable<Hand> {
    private val combi = run {
        val counts = combination.map { it.second }
        when {
            counts.all { it == 1 } -> Combi.HIGH_CARD
            counts.max() > 4 -> Combi.FIVE_OF_A_KIND
            counts.contains(4) -> Combi.FOUR_OF_A_KIND
            counts.contains(3) && counts.contains(2) -> Combi.FULL_HOUSE
            counts.contains(3) -> Combi.THREE_OF_A_KIND
            counts.count { it == 2 } == 2 -> Combi.TWO_PAIRS
            counts.contains(2) -> Combi.ONE_PAIR
            else -> throw IllegalStateException("")
        }
    }

    override fun compareTo(other: Hand): Int {
        if (combi.compareTo(other.combi) != 0) {
            return combi.compareTo(other.combi)
        } else {
            for (i in 0 until 5) {
                val card = Card(origString[i].toString(), part1)
                val otherCard = Card(other.origString[i].toString(), part1)
                if (card.compareTo(otherCard) != 0) {
                    return otherCard.compareTo(card)
                }
            }
        }
        return 0
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val sortedHands = input.map { line ->
            val (cardsString, bid) = line.split(" ")
            val cards = cardsString.map { Card(it.toString()) }
            val combination = cards.groupBy { it }
                .mapValues { it.value.count() }
                .entries.sortedWith(compareByDescending<Map.Entry<Card, Int>> { it.value }.thenBy { it.key })
                .map { it.key to it.value }
            Hand(combination, bid.toInt(), cardsString, true)
        }.sorted()
        return sortedHands.withIndex().sumOf { (index, hand) ->
            println(index.toString() + " " + hand.origString + " " + hand.bid)
            (index + 1) * hand.bid
        }
    }

    fun part2(input: List<String>): Int {
        val sortedHands = input.map { line ->
            val (cardsString, bid) = line.split(" ")
            val cards = cardsString.map { Card(it.toString(), false) }
            val combination = cards.groupBy { it }
                .mapValues { it.value.count() }
                .entries.sortedWith(compareByDescending<Map.Entry<Card, Int>> { it.value }.thenBy { it.key })
                .map { it.key to it.value }.toMutableList()
            combination.filter { it.first.value == "J" }.forEach { jokerPair ->
                if (combination.size == 1) {
                    combination.remove(jokerPair)
                    combination.add(Card("A", false) to 5)
                } else if (combination[0] == jokerPair) {
                    combination[1] = combination[1].first to combination[1].second + jokerPair.second
                } else {
                    combination[0] = combination[0].first to combination[0].second + jokerPair.second
                }
                combination.remove(jokerPair)
            }
            Hand(combination, bid.toInt(), cardsString, false)
        }.sorted()
        return sortedHands.withIndex().sumOf { (index, hand) ->
            println(index.toString() + " " + hand.origString + " " + hand.bid + " " + hand.combination)
            (index + 1) * hand.bid
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
