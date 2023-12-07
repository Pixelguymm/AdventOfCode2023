import utils.lines
import utils.resource

fun main() {
    val input = resource("Day2.txt").lines()
    Day2(input).showResults()
}

class Day2(private val lines: List<String>) : Day() {
    private val bounds = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    private fun parseInputs(line: String) = line.substringAfter("Game ").split(":").let { pair ->
        (pair.first().toIntOrNull() ?: 0) to pair.last().split(";").map { record ->
            record.trim().split(",").associate { color ->
                color.trim().split(" ").let {
                    it.last() to (it.first().toIntOrNull() ?: 0)
                }
            }
        }
    }

    override fun part1() = lines.sumOf { line ->
        val (id, inputs) = parseInputs(line)

        if (inputs.none { bounds.any { (color, bound) -> it.getOrDefault(color, 0) > bound } }) id else 0
    }

    override fun part2() = lines.sumOf { line ->
        val (_, inputs) = parseInputs(line)

        fun List<Map<String, Int>>.maxForKey(key: String) = maxOf { it.getOrDefault(key, 0) }

        inputs.reduce { res, current ->
            mapOf(
                "red" to listOf(res, current).maxForKey("red"),
                "green" to listOf(res, current).maxForKey("green"),
                "blue" to listOf(res, current).maxForKey("blue")
            )
        }.values.reduce(Int::times)
    }
}
