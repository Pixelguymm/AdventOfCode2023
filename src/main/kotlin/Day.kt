abstract class Day {
    abstract fun part1(): Any

    abstract fun part2(): Any

    fun showResults() {
        println(part1())
        println(part2())
    }
}
