package utils

import kotlin.math.pow

operator fun Char.plus(other: Char) = this.toString() + other

fun Int.pow(n: Int) = this.toDouble().pow(n)
fun Long.pow(n: Int) = this.toDouble().pow(n)
