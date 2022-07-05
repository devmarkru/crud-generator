package ru.devmark.generator

import ru.devmark.meta.Entity

class EntityGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Entity.kt"

    override fun generate(entity: Entity): String {
        val code = StringBuilder()
        code.appendLine("data class ${entity.name}Entity(")
        code.appendLine("    val id: Int = 0,")
        entity.fields.forEach { field ->
            code.appendLine("    val ${field.name}: ${field.type.kotlinType},")
        }
        code.appendLine(")").appendLine()
        return code.toString()
    }
}