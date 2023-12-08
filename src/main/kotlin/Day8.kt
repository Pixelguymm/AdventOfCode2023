import utils.lines
import utils.resource
import utils.splitByWhitespace
import kotlin.math.min

fun main() {
    val (instructions, lines) = resource("Day8.txt").splitByWhitespace().let {
        it.first().toList() to it.last().lines()
    }
    Day8(instructions, lines).showResults()
}

class Day8(private val instructions: List<Char>, lines: List<String>) : Day() {
    private val map = lines.associate { line ->
        val (key, left, right) = Regex("\\w{3}").findAll(line).map { it.value }.toList()
        key to (left to right)
    }

    private fun Pair<String, String>.move(direction: Char) = when (direction) {
        'L' -> first
        'R' -> second
        else -> null
    }

    private fun countSteps(start: String, stopWhen: (String) -> Boolean): Int {
        var current = start
        var count = 0

        while (!stopWhen(current)) {
            current = map[current]?.move(instructions[count % instructions.count()]) ?: current
            count++
        }

        return count
    }

    private fun List<Long>.getLcm() = reduce { first, second ->
        var gcd = 1L
        val upper = min(first, second) / 2
        for (i in 2..upper) {
            if (first % i == 0L && second % i == 0L) {
                gcd = i
                break
            }
        }

        (first * second) / gcd
    }

    override fun part1() = countSteps("AAA") { it == "ZZZ" }

    override fun part2(): Long {
        val periods = map.keys.filter {
            it.endsWith('A')
        }.map { start ->
            countSteps(start) { it.endsWith('Z') }.toLong()
        }

        return periods.getLcm()
    }
}
