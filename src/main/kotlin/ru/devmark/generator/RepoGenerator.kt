package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType
import ru.devmark.util.toSnakeCase

class RepoGenerator : KotlinCodeGenerator() {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Repository.kt"

    override fun getPackageName(entity: Entity): String =
        "${entity.basePackage}.repository"

    override fun getTemplateName(): String = "RepositoryTemplate.kt"

    override fun processTemplate(template: String, entity: Entity): String {
        var code = template
        code = code.replace("SNAKE_ENTITY", entity.name.toSnakeCase())
        code = code.replace("ENTITY", entity.name)

        addImports(entity)

        code = code.replace(
            "                        FIELD_NAMES_VALUES",
            entity.fields.joinToString(
                separator = ",$newLine",
            ) { "                        ${it.name.toSnakeCase()} = :${it.name}" }
        )

        code = code.replace(
            "                        FIELD_NAMES",
            entity.fields.joinToString(
                separator = ",$newLine",
            ) { "                        ${it.name.toSnakeCase()}" })

        code = code.replace(
            "                        FIELD_VALUES",
            entity.fields.joinToString(
                separator = ",$newLine",
            ) { "                        :${it.name}" }
        )

        code = code.replace(
            "                    FIELD_MAPPING",
            entity.fields.joinToString(
                separator = newLine,
            ) { "                    \"${it.name}\" to entity.${it.name}," }
        )

        val rowMapperFields = entity.fields.joinToString(separator = System.lineSeparator()) { field ->
            val methodName = if (field.nullable) {
                when (field.type) {
                    FieldType.INT -> "getIntOrNull(FIELD)"
                    FieldType.LONG -> "getLongOrNull(FIELD)"
                    FieldType.STRING -> "getString(FIELD)"
                    FieldType.BOOL -> "getBooleanOrNull(FIELD)"
                    FieldType.BIG_DECIMAL -> "getBigDecimal(FIELD)"
                    FieldType.DATE_TIME -> "getTimestamp(FIELD)?.toLocalDateTime()"
                }
            } else {
                when (field.type) {
                    FieldType.INT -> "getInt(FIELD)"
                    FieldType.LONG -> "getLong(FIELD)"
                    FieldType.STRING -> "getString(FIELD)"
                    FieldType.BOOL -> "getBoolean(FIELD)"
                    FieldType.BIG_DECIMAL -> "getBigDecimal(FIELD)"
                    FieldType.DATE_TIME -> "getTimestamp(FIELD).toLocalDateTime()"
                }
            }
            val mapper = methodName.replace("FIELD", "\"${field.name.toSnakeCase()}\"")
            "                ${field.name} = rs.$mapper,"
        }
        code = code.replace("                ROW_MAPPER_FIELDS", rowMapperFields)
        return code
    }

    private fun addImports(entity: Entity) {
        import("org.springframework.jdbc.core.RowMapper")
        import("org.springframework.jdbc.core.simple.JdbcClient")
        import("org.springframework.jdbc.support.GeneratedKeyHolder")
        import("org.springframework.stereotype.Repository")

        import("${entity.basePackage}.entity.${entity.name}Entity")

        entity.importNullableType(FieldType.BOOL)
        entity.importNullableType(FieldType.INT)
        entity.importNullableType(FieldType.LONG)
    }

    private fun Entity.importNullableType(fieldType: FieldType) {
        this.fields.firstOrNull { it.type == fieldType && it.nullable }
            ?.let { field -> import("${this.basePackage}.util.get${field.type.kotlinType}OrNull") }
    }

    private companion object {
        val newLine: String = System.lineSeparator()
    }
}
