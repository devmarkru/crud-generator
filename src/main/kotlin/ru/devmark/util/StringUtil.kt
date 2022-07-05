package ru.devmark.util

fun String.toSnakeCase(): String =
    this.mapIndexed { index, c ->
        if (index == 0) {
            c.lowercaseChar()
        } else if (c == c.uppercaseChar()) {
            "_${c.lowercaseChar()}"
        } else {
            c
        }
    }.joinToString(separator = "")

fun String.toCamelCase(): String =
    this.first().lowercaseChar() + this.substring(1)
