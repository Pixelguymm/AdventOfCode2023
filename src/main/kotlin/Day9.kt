import utils.lines
import utils.resource

fun main() {
    val sequences = resource("Day9.txt").lines().map {
        it.split(" ").map(String::toInt)
    }
    Day9(sequences).showResults()
}

class Day9(private val sequences: List<List<Int>>): Day() {
    private fun List<Int>.nextValue(): Int {
        return if (distinct().count() == 1) {
            first()
        } else {
            last() + mapIndexedNotNull { i, num ->
                getOrNull(i + 1)?.minus(num)
            }.nextValue()
        }
    }

    private fun List<Int>.previousValue(): Int {
        return if (distinct().count() == 1) {
            first()
        } else {
            first() - mapIndexedNotNull { i, num ->
                getOrNull(i + 1)?.minus(num)
            }.previousValue()
        }
    }

    override fun part1() = sequences.sumOf {
        it.nextValue()
    }

    override fun part2() = sequences.sumOf {
        it.previousValue()
    }
}
