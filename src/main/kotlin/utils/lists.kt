package utils

typealias Matrix<T> = List<List<T>>

// Just being extra
operator fun <T> Matrix<T>.get(i1: Int, i2: Int) = this[i1][i2]
fun <T> Matrix<T>.getOrNull(i1: Int, i2: Int) = this.getOrNull(i1)?.getOrNull(i2)

operator fun <T> List<T>.get(r: IntRange): List<T> = this.slice(r)
operator fun <T> Matrix<T>.get(r1: IntRange, r2: IntRange): List<List<T>> = this.slice(r1).map { it.slice(r2) }

fun <T> Matrix<T>.indexed() = this.mapIndexed { i1, l ->
    l.mapIndexed { i2, t ->
        Pair(i1, i2) to t
    }
}

fun <T> Matrix<T>.flipped() = List(this.first().size) { i2 -> List(this.size) { i1 -> this[i1, i2]} }
