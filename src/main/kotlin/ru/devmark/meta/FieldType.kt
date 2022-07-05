package ru.devmark.meta

enum class FieldType(val text: String, val kotlinType: String,) {
    INT("", "Int"),
    STRING("", "String"),
    BOOL("", "Boolean"),
    DATE_TIME("", "LocalDateTime"),
}