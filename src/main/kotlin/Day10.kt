import utils.get
import utils.getOrNull
import utils.lines
import utils.resource

fun main() {
    val pipes = resource("Day10.txt").lines().map { l ->
        l.toList().map {
            Cell(Pipe.fromChar(it), false)
        }
    }
    Day10(pipes).showResults()
}

enum class Direction(val relativeX: Int, val relativeY: Int) {
    U(-1, 0),
    D(1, 0),
    L(0, -1),
    R(0, 1);

    fun opposite() = when (this) {
        U -> D
        D -> U
        L -> R
        R -> L
    }
}

enum class Pipe(val char: Char, val connected: List<Direction> = listOf()) {
    UD('|', listOf(Direction.U, Direction.D)),
    LR('-', listOf(Direction.L, Direction.R)),
    UR('L', listOf(Direction.U, Direction.R)),
    UL('J', listOf(Direction.U, Direction.L)),
    DL('7', listOf(Direction.D, Direction.L)),
    DR('F', listOf(Direction.D, Direction.R)),
    None('.'),
    Start('S', Direction.entries);

    companion object {
        fun fromChar(char: Char) = entries.find { it.char == char } ?: None
    }
}

data class Cell(val pipe: Pipe, var traversed: Boolean)

class Day10(private val cells: MutableList<MutableList<Cell>>) : Day() {
    private val loop: List<Pair<Int, Int>> by lazy {
        var x = cells.indexOfFirst { row -> row.any { it.pipe == Pipe.Start } }
        var y = cells[x].indexOfFirst { it.pipe == Pipe.Start }

        var current = cells[x, y]
        val loop = mutableListOf<Pair<Int, Int>>()

        while (!current.traversed) {
            current.traversed = true
            loop.add(Pair(x, y))

            for (d in current.pipe.connected) {
                val c = cells.getOrNull(x + d.relativeX, y + d.relativeY) ?: continue

                if (d.opposite() in c.pipe.connected && !c.traversed) {
                    x += d.relativeX
                    y += d.relativeY
                    current = c
                    break
                }
            }
        }

        loop
    }

    private fun getImpliedPipe(x: Int, y: Int): Pipe {
        val connected = Direction.entries.filter { d ->
            cells.getOrNull(x + d.relativeX, y + d.relativeY)
                ?.pipe?.connected
                ?.contains(d.opposite()) ?: false
        }

        return Pipe.entries.find { it.connected.containsAll(connected) } ?: Pipe.None
    }

    override fun part1() = loop.count() / 2

    override fun part2(): Int {
        var count = 0

        cells.forEachIndexed { x, row ->
            var inLoop = false
            var flipOn = listOf(Pipe.UD)

            row.forEachIndexed { y, cell ->
                val p = if (cell.pipe == Pipe.Start) {
                    getImpliedPipe(x, y)
                } else {
                    cell.pipe
                }

                if (Pair(x, y) in loop) {
                    if (p in flipOn) {
                        inLoop = !inLoop
                    }
                    flipOn = when (p) {
                        Pipe.UR -> listOf(Pipe.DL)
                        Pipe.DR -> listOf(Pipe.UL)
                        Pipe.LR -> flipOn
                        else -> listOf(Pipe.UD)
                    }
                } else if (inLoop) {
                    count++
                }
            }
        }

        return count
    }
}
