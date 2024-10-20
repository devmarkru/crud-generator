package ru.devmark.generator

import ru.devmark.meta.Entity

class EntityGenerator : KotlinCodeGenerator() {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Entity.kt"

    override fun getPackageName(entity: Entity): String = "${entity.basePackage}.entity"

    override fun getTemplateName(): String? = null

    override fun processTemplate(template: String, entity: Entity): String {
        val code = StringBuilder()

        entity.fields
            .mapNotNull { it.type.requiredImport }
            .forEach { import(it) }

        code.appendLine("data class ${entity.name}Entity(")
        code.appendLine("    val id: Int = 0,")
        entity.fields.forEach { field ->
            code.append("    val ${field.name}: ${field.type.kotlinType}")
            if (field.nullable) {
                code.append("?")
            } else {
                field.type.defaultValue
                    ?.let { defaultValue -> code.append(" = ").append(defaultValue) }
            }
            code.appendLine(",")
        }
        code.appendLine(")")
        return code.toString()
    }
}
