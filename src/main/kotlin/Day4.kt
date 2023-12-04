import kotlin.math.roundToInt

fun main() {
    val lines = resource("Day4.txt").lines()
    Day4(lines).let {
        println(it.part1())
        println(it.part2())
    }
}

class Day4(lines: List<String>) {
    private var cards: List<Card>

    init {
        cards = lines.map { l ->
            val (winning, scratched) = l.split("|").map { n ->
                n.trim().split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toIntOrNull() ?: 0 }
            }

            Card(winning, scratched)
        }
    }

    fun part1() = cards.sumOf { card ->
        val exp = card.getOverlap() - 1
        if (exp < 0) 0
        else 2.pow(exp).roundToInt()
    }

    fun part2() = cards.sumOfIndexed { i, card ->
        val overlap = card.getOverlap()

        for (i2 in 1..overlap) {
            cards.getOrNull(i + i2)?.apply { this.multiplier += card.multiplier } ?: break
        }

        card.multiplier
    }
}

data class Card(val winning: List<Int>, val scratched: List<Int>, var multiplier: Int = 1) {
    fun getOverlap() = scratched.count { it in winning }
}
