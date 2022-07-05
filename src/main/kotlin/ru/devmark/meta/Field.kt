package ru.devmark.meta

data class Field(
    val name: String,
    val type: FieldType,
    val nullable: Boolean,
)
