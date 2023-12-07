import utils.lines
import utils.pow
import utils.resource
import kotlin.math.*

fun main() {
    val lines = resource("Day6.txt").lines()
    Day6(lines).showResults()
}

class Day6(lines: List<String>) : Day() {
    private val records = lines.map {
        it.split(Regex("\\s+")).drop(1).mapNotNull(String::toLongOrNull)
    }.let { it.first() zip it.last() }

    private val singleRecord = lines.mapNotNull {
        it.replace(Regex("\\s+"), "").substringAfter(":").toLongOrNull()
    }.let { it.first() to it.last() }

    private fun getAmount(record: Pair<Long, Long>) = record.let { (time, distance) ->
        // -(x^2) + time * x - distance = 0
        val d = time.pow(2) - 4 * distance
        val x1 = (-time - sqrt(d)) / -2
        val x2 = (-time + sqrt(d)) / -2

        val (low, high) = listOf(x1, x2).map { it.absoluteValue }.sorted()

        (ceil(high) - floor(low)).roundToInt() - 1
    }

    override fun part1() = records.map { getAmount(it) }.reduce { a, b -> a * b }

    override fun part2() = getAmount(singleRecord)
}
