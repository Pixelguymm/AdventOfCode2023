import utils.get
import utils.lines
import utils.resource
import utils.splitNotEmpty

fun main() {
    val records = resource("Day12.txt").lines().map { l ->
        val (first, second) = l.split(" ")
        val nums = second.split(",").map { it.toInt() }
        first to nums
    }
    Day12(records).showResults()
}

class Day12(private val records: List<Pair<String, List<Int>>>) : Day() {
    private val permutationCache = mutableMapOf<Int, List<String>>()

    private fun permutationsForLength(n: Int): List<String> {
        var permutations = listOf("")

        (0..<n).forEach { _ ->
            permutations = permutations.flatMap { partial ->
                listOf('#', '.').map { c ->
                    partial + c
                }
            }
        }

        return permutations
    }

    inner class Chunk(private val chars: String, private val known: Boolean) {
        val permutations by lazy {
            if (known) {
                listOf(chars)
            } else {
                permutationCache.getOrElse(chars.length) {
                    permutationsForLength(chars.length)
                }
            }
        }
    }

    private fun List<Pair<String, List<Int>>>.numberOfOptions() = sumOf { r ->
        var springs = r.first

        var chunks = mutableListOf<Chunk>()
        while (springs.isNotEmpty()) {
            springs.takeWhile { it == '?' }.let {
                chunks.add(Chunk(it, false))
                if (it.isNotEmpty()) springs = springs.drop(it.length)
            }
            springs.takeWhile { it != '?' }.let {
                chunks.add(Chunk(it, true))
                if (it.isNotEmpty()) springs = springs.drop(it.length)
            }
        }

        var options = listOf("")
        while (chunks.isNotEmpty()) {
            val postfixes = chunks.take(2).let { (unknown, known) ->
                unknown.permutations.flatMap { first ->
                    known.permutations.map { second -> first + second }
                }
            }

            chunks = chunks.drop(2).toMutableList()

            options = options.flatMap { partial ->
                postfixes.map { partial + it }.filter { o ->
                    o.splitNotEmpty(".").map { it.length }.let { groups ->
                        groups.isEmpty() ||
                            groups.size <= r.second.size &&
                            groups.dropLast(1) == r.second[0..<(groups.lastIndex)] &&
                            groups.last() <= r.second[groups.lastIndex]
                    }
                }
            }
        }

        options.count { o ->
            o.splitNotEmpty(".").map { it.length }.let { groups ->
                groups == r.second
            }
        }.toLong()
    }

    override fun part1() = records.numberOfOptions()

    override fun part2() = "Part 2 is not implemented."
}
