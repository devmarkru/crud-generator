package ru.devmark.meta

enum class FieldType(
    val kotlinType: String,
    val defaultValue: String? = null,
    val requiredImport: String? = null,
) {
    INT("Int"),
    LONG("Long"),
    STRING("String"),
    BOOL("Boolean"),
    BIG_DECIMAL("BigDecimal", null, "java.math.BigDecimal"),
    DATE_TIME("LocalDateTime", "LocalDateTime.now()", "java.time.LocalDateTime"),
}
