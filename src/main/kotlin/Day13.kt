import utils.Matrix
import utils.flipped
import utils.lines
import utils.resource
import utils.splitByWhitespace

fun main() {
    val terrains = resource("Day13.txt").splitByWhitespace().map { t ->
        t.lines().map { it.toList() }
    }
    Day13(terrains).showResults()
}

class Day13(private val terrains: List<Matrix<Char>>) : Day() {
    private fun List<Char>.terrainDigest() = fold(0) { res, char ->
        res shl 1 or if (char == '#') 1 else 0
    }

    private fun Matrix<Char>.findSymmetry() = map { it.terrainDigest() }.let { digests ->
        outer@ for (i in 0..<digests.lastIndex) {
            for (offset in 0..(digests.lastIndex - i)) {
                val d1 = digests.getOrNull(i - offset) ?: return@let i + 1
                val d2 = digests.getOrNull(i + 1 + offset) ?: return@let i + 1
                if (d1 != d2) {
                    continue@outer
                }
            }
        }

        null
    }

    private fun Matrix<Char>.findSmudgedSymmetry() = map { it.terrainDigest() }.let { digests ->
        outer@ for (i in 0..<digests.lastIndex) {
            var flipped = false
            for (offset in 0..(digests.lastIndex - i)) {
                val d1 = digests.getOrNull(i - offset)
                val d2 = digests.getOrNull(i + 1 + offset)

                if (d1 == null || d2 == null) {
                    if (flipped) return@let i + 1
                    else continue@outer
                }

                val diff = d1 xor d2

                if (diff != 0) {
                    if (!flipped && (diff and (diff - 1)) == 0) {
                        flipped = true
                    } else {
                        continue@outer
                    }
                }
            }
        }

        null
    }

    override fun part1() = terrains.sumOf {
        (it.findSymmetry()?.times(100) ?: it.flipped().findSymmetry() ?: 0)
    }

    override fun part2() = terrains.sumOf {
        (it.findSmudgedSymmetry()?.times(100) ?: it.flipped().findSmudgedSymmetry() ?: 0)
    }
}