import kotlin.system.measureTimeMillis

abstract class Day {
    abstract fun part1(): Any

    abstract fun part2(): Any

    fun showResults() {
        measureTimeMillis {
            println(part1())
        }.also { println("Time in ms: $it") }
        measureTimeMillis {
            println(part2())
        }.also { println("Time in ms: $it") }
    }
}
