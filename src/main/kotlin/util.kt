import java.io.IOException
import kotlin.math.pow

fun resource(name: String) = {}.javaClass.getResource(name)?.readText()?.trim()
    ?: throw IOException("No resource named \"$name\"")

fun String.lines() = this.split(Regex("[\n\r]+"))
fun String.splitByWhitespace() = this.split(Regex("([\n\r]+)\\1+"))

operator fun Char.plus(other: Char) = this.toString() + other

// Just being extra
operator fun <T> List<List<T>>.get(i1: Int, i2: Int) = this[i1][i2]
fun <T> List<List<T>>.getOrNull(i1: Int, i2: Int) = this.getOrNull(i1)?.getOrNull(i2)

operator fun <T> List<T>.get(r: IntRange): List<T> = this.slice(r)
operator fun <T> List<List<T>>.get(r1: IntRange, r2: IntRange): List<List<T>> = this.slice(r1).map { it.slice(r2) }

fun <T> List<List<T>>.indexed() = this.mapIndexed { i1, l ->
    l.mapIndexed { i2, t ->
        Pair(i1, i2) to t
    }
}

fun Int.pow(n: Int) = this.toDouble().pow(n)
fun Long.pow(n: Int) = this.toDouble().pow(n)

fun <T> List<T>.sumOfIndexed(transform: (Int, T) -> Int) = this.mapIndexed(transform).sum()
