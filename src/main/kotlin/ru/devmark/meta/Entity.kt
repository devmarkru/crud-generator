package ru.devmark.meta

data class Entity(
    val basePackage: String,
    val name: String,
    val fields: MutableList<Field> = mutableListOf(),
)
