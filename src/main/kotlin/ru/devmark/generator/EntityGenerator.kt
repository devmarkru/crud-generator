package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType

class EntityGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Entity.kt"

    override fun generate(entity: Entity): String {
        val code = StringBuilder()
        code.appendLine("package ${entity.basePackage}.entity")
        code.appendLine()
        if (entity.fields.any { it.type == FieldType.DATE_TIME }) {
            code.appendLine("import java.time.LocalDateTime")
            code.appendLine()
        }
        code.appendLine("data class ${entity.name}Entity(")
        code.appendLine("    val id: Int = 0,")
        entity.fields.forEach { field ->
            code.append("    val ${field.name}: ${field.type.kotlinType}")
            if (field.nullable) {
                code.append("?")
            }
            field.type.defaultValue
                ?.let { defaultValue -> code.append(" = ").append(defaultValue) }
            code.appendLine(",")
        }
        code.appendLine(")").appendLine()
        return code.toString()
    }
}
