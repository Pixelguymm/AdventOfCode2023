import utils.get
import utils.lines
import utils.resource
import utils.splitByWhitespace
import utils.splitNotEmpty

fun main() {
    val groups = resource("Day5.txt").splitByWhitespace().map { it.lines() }
    Day5(groups).showResults()
}

class Day5(groups: List<List<String>>) : Day() {
    private val seeds = groups[0, 0].splitNotEmpty(" ").drop(1).mapNotNull(String::toLongOrNull)

    private val maps = groups.drop(1).map { g ->
        g.drop(1).map { l ->
            val (dest, source, length) = l.splitNotEmpty(" ").mapNotNull(String::toLongOrNull).also {
                // Shouldn't happen, just being safe.
                if (it.count() < 3) throw Exception("Invalid input")
            }
            MapEntry(source, dest, length)
        }
    }

    private fun processSeed(s: Long): Long {
        var n = s

        maps.forEach { n = it.process(n) }

        return n
    }

    override fun part1() = seeds.minOf { s ->
        processSeed(s)
    }

    override fun part2(): Long {
        val seedRanges = mutableListOf<LongRange>()
        for (i in 0..<(seeds.count() - 1) step 2) {
            val (start, len) = seeds[i..(i + 1)]
            seedRanges.add(start..(start + len))
        }

        return seedRanges.minOf { r ->
            r.minOf { s -> processSeed(s) }
        }
    }
}

class MapEntry(from: Long, to: Long, length: Long) {
    val range = from..(from + length)
    val addend = to - from
}

fun List<MapEntry>.process(n: Long) = find { n in it.range }?.let { n + it.addend } ?: n
