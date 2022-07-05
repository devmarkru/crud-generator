package ru.devmark.generator

import ru.devmark.meta.Entity

interface CodeGenerator {

    fun getFileName(entity: Entity): String

    fun generate(entity: Entity): String
}