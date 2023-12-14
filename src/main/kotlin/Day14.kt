import utils.Matrix
import utils.flipped
import utils.lines
import utils.resource

fun main() {
    val grid = resource("Day14.txt").lines().map { t ->
        t.toList()
    }

    Day14(grid).showResults()
}

class Day14(private val grid: Matrix<Char>) : Day() {
    private fun Matrix<Char>.tilt(flipped: Boolean, toFront: Boolean): Matrix<Char> {
        val lines = this.let {
            if (flipped) it.flipped()
            else it
        }.map { it.joinToString("") }

        return lines.map { l ->
            l.split("#").joinToString("#") { s ->
                s.toList().sortedBy { (it == 'O') != toFront }.joinToString("")
            }.toList()
        }.let {
            if (flipped) it.flipped()
            else it
        }
    }

    override fun part1(): Int {
        val tilted = grid.tilt(flipped = true, toFront = true)

        return tilted.withIndex().sumOf { (i, row) ->
            row.filter { it == 'O' }.sumOf { tilted.size - i }
        }
    }

    override fun part2(): Int {
        fun Matrix<Char>.doCycle() = this
            .tilt(flipped = true, toFront = true)
            .tilt(flipped = false, toFront = true)
            .tilt(flipped = true, toFront = false)
            .tilt(flipped = false, toFront = false)

        var current = grid
        val cycleMap = mutableMapOf(0L to current)

        var i = 1L
        while (i <= 1E9) {
            current = current.doCycle()

            cycleMap.entries.find { it.value == current }?.key?.let { start ->
                val length = i - start

                val cycleOffset = ((1E9 - start) % length).toLong()

                return cycleMap[start + cycleOffset]?.let { end ->
                    end.withIndex().sumOf { (i, row) ->
                        row.filter { it == 'O' }.sumOf { end.size - i }
                    }
                } ?: 0
            } ?: run { cycleMap[i++] = current }
        }

        return 0
    }
}
