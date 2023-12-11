import utils.Matrix
import utils.lines
import utils.resource
import kotlin.math.absoluteValue

fun main() {
    val grid = resource("Day11.txt").lines().map { l ->
        l.toList()
    }
    Day11(grid).showResults()
}

data class Galaxy(val position: Pair<Int, Int>, val id: Int)

class Day11(grid: Matrix<Char>) : Day() {
    private val galaxies by lazy {
        var galaxyCount = 0

        grid.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, c ->
                if (c == '#') Galaxy(x to y, ++galaxyCount)
                else null
            }
        }
    }

    private val expandedCols by lazy {
        val cols = mutableListOf<Int>()

        for (i in 0..<grid.first().size) {
            if (grid.all { it.getOrNull(i) == '.' }) cols.add(i)
        }

        cols
    }

    private val expandedRows by lazy {
        val rows = mutableListOf<Int>()

        grid.forEachIndexed { i, row ->
            if (row.all { it == '.' }) rows.add(i)
        }

        rows
    }

    private fun List<Galaxy>.expand(factor: Int) = map { (position, id) ->
        val x = position.first + expandedCols.count { it < position.first } * (factor - 1)
        val y = position.second + expandedRows.count { it < position.second } * (factor - 1)

        Galaxy(x to y, id)
    }

    private fun Galaxy.distanceTo(other: Galaxy): Long {
        val diffX = (this.position.first - other.position.first).absoluteValue
        val diffY = (this.position.second - other.position.second).absoluteValue

        return (diffX + diffY).toLong()
    }

    private fun List<Galaxy>.sumOfDistances() = sumOf { g1 ->
        filter { g2 -> g1.id < g2.id }.sumOf { g2 ->
            g1.distanceTo(g2)
        }
    }

    override fun part1() = galaxies.expand(2).sumOfDistances()

    override fun part2() = galaxies.expand(1_000_000).sumOfDistances()
}