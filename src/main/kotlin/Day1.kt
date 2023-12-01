fun main() {
    val input = resource("Day1.txt").lines()
    Day1(input).let {
        println(it.part1())
        println(it.part2())
    }
}

class Day1(private val lines: List<String>) {
    private val numbers = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9'
    )

    fun part1() = lines.sumOf { line ->
        line.filter { it.isDigit() }.let {
            it.first() + it.last()
        }.toIntOrNull() ?: 0
    }

    fun part2() = lines.sumOf { line ->
        val matches = Regex("(?=(${numbers.keys.joinToString("|")}|\\d))").findAll(line)
        matches.map {
            val value = it.groupValues[1]
            if (value.count() > 1) numbers.getOrDefault(value, '0')
            else value[0]
        }.let {
            it.first() + it.last()
        }.toIntOrNull() ?: 0
    }
}
