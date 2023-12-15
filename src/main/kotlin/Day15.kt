import utils.resource

fun main() {
    val parts = resource("Day15.txt").split(",")
    Day15(parts).showResults()
}

typealias LabeledLens = Pair<String, Int?>

class Day15(private val parts: List<String>) : Day() {

    private fun String.hashDigest() = fold(0) { res, cur ->
        (res + cur.code) * 17 % 256
    }

    override fun part1() = parts.sumOf { p ->
        p.hashDigest()
    }

    override fun part2() = parts.asSequence()
        .map { p ->
            val label = p.takeWhile { it.isLetter() }
            val lens = p.takeLastWhile { it.isDigit() }.toIntOrNull()

            LabeledLens(label, lens)
        }
        .groupBy { it.first.hashDigest() }.entries
        .sumOf { (box, lenses) ->
            lenses
                .fold(mutableMapOf<String, LabeledLens>()) { res, cur ->
                    res.apply {
                        if (cur.second == null) this.remove(cur.first)
                        else this[cur.first] = cur
                    }
                }.values
                .filter { it.second != null }.withIndex()
                .sumOf { (i, lens) ->
                    (lens.second ?: 0) * (i + 1) * (box + 1)
                }
        }
}
