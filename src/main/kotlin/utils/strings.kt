package utils

fun String.splitNotEmpty(vararg delimiters: String, ignoreCase: Boolean = false, limit: Int = 0) =
    split(*delimiters, ignoreCase = ignoreCase, limit = limit).filter { it.isNotEmpty() }
fun String.splitNotEmpty(vararg delimiters: Char, ignoreCase: Boolean = false, limit: Int = 0) =
    split(*delimiters, ignoreCase = ignoreCase, limit = limit).filter { it.isNotEmpty() }
