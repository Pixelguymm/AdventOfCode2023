import utils.lines
import utils.resource

fun main() {
    val lines = resource("Day7.txt").lines()
    Day7(lines).showResults()
}

class Day7(lines: List<String>) : Day() {
    private val hands: List<Hand> = lines.map { l ->
        l.split(" ").let { it.first() to (it.last().toIntOrNull() ?: 0) }
    }

    private fun getRank(hand: Hand) = hand.first
        .toList()
        .partition { it != '*' }.let { (cards, jokers) ->
            cards.groupBy { it }
            .map { it.value.count() }
            .sortedDescending().let {
                when ((it.getOrNull(0) ?: 0) + jokers.count()) {
                    5 -> 1
                    4 -> 2
                    3 -> 5 - it[1]
                    2 -> 7 - it[1]
                    else -> 7
                }
            }
        }

    private val cardMap = mapOf(
        'A' to 1, 'K' to 2, 'Q' to 3, 'J' to 4, 'T' to 5, '9' to 6, '8' to 7,
        '7' to 8, '6' to 9, '5' to 10, '4' to 11, '3' to 12, '2' to 13, '*' to 14
    )

    private fun List<Hand>.getValueSum() = sortedByDescending { hand ->
        getRank(hand) * 10e12 + hand.first.toList().fold(0L) { res, cur ->
            res * 100 + cardMap.getOrDefault(cur, 0)
        }
    }.withIndex().sumOf { (i, it) -> (i + 1) * it.second }

    override fun part1() = hands.getValueSum()

    override fun part2() = hands.map {
        it.first.replace('J', '*') to it.second
    }.getValueSum()
}

typealias Hand = Pair<String, Int>