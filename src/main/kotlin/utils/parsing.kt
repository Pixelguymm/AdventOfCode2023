package utils

import java.io.IOException

fun resource(name: String) = {}.javaClass.getResource("../$name")?.readText()?.trim()
    ?: throw IOException("No resource named \"$name\"")

fun String.lines() = this.split(Regex("[\n\r]+"))
fun String.splitByWhitespace() = this.split(Regex("([\n\r]+)\\1+"))
