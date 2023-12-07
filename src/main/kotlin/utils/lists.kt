package utils

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

fun <T> List<T>.sumOfIndexed(transform: (Int, T) -> Int) = this.mapIndexed(transform).sum()
