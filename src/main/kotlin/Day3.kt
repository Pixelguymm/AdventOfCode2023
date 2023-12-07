import utils.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = resource("Day3.txt").lines().map(String::toList)
    Day3(input).showResults()
}

class Day3(private val charMatrix: List<List<Char>>) : Day() {
    private fun List<*>.getRangeInBounds(i: Int) =
        max(0, i - 1)..min(count() - 1, i + 1)

    private fun <T> List<List<T>>.getNeighbours(i1: Int, i2: Int) =
        this[getRangeInBounds(i1), this[i1].getRangeInBounds(i2)]

    private fun List<List<Char>>.hasNeighbour(i1: Int, i2: Int) =
        getNeighbours(i1, i2)
            .flatten()
            .any {
                !it.isDigit() && it != '.'
            }

    private fun List<List<Char>>.getFullNumber(i1: Int, i2: Int): Int {
        var start = i2
        while (getOrNull(i1, start - 1)?.isDigit() == true) {
            --start
        }
        var numStr = ""
        while (getOrNull(i1, start)?.isDigit() == true) {
            numStr += this[i1, start++]
        }
        return numStr.toIntOrNull() ?: 0
    }

    override fun part1(): Int {
        val nums = mutableListOf<Int>()

        for (i1 in 0..<charMatrix.count()) {
            var i2 = 0
            while (i2 in 0..<charMatrix[0].count()) {
                if (charMatrix[i1, i2].isDigit() && charMatrix.hasNeighbour(i1, i2)) {
                    nums.add(charMatrix.getFullNumber(i1, i2))
                    while (charMatrix.getOrNull(i1, i2)?.isDigit() == true) {
                        i2++
                    }
                } else {
                    i2++
                }
            }
        }

        return nums.sum()
    }

    override fun part2(): Int {
        val nums = mutableListOf<Int>()

        charMatrix.indexed().flatten().filter { it.second == '*' }.map { (coordinates, _) ->
            charMatrix.indexed().getNeighbours(coordinates.first, coordinates.second)
        }.forEach { neighbours ->
            val digits = mutableListOf<Pair<Pair<Int, Int>, Char>>()

            for (row in neighbours) {
                var i = 0
                while (i in 0..<neighbours.count()) {
                    if (row[i].second.isDigit()) {
                        digits.add(row[i])
                        while (row.getOrNull(i)?.second?.isDigit() == true) {
                            i++
                        }
                    } else {
                        i++
                    }
                }
            }
            if (digits.count() < 2) return@forEach

            nums.add(digits.map { charMatrix.getFullNumber(it.first.first, it.first.second) }.reduce(Int::times))
        }

        return nums.sum()
    }
}
