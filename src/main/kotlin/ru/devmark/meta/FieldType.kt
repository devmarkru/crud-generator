package ru.devmark.meta

enum class FieldType(val text: String, val kotlinType: String,) {
    INT("", "Int"),
    LONG("", "Long"),
    STRING("", "String"),
    BOOL("", "Boolean"),
    DATE_TIME("", "LocalDateTime"),
}