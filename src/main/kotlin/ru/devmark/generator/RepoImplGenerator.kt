package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType
import ru.devmark.util.toSnakeCase

class RepoImplGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}RepositoryImpl.kt"

    override fun generate(entity: Entity): String {
        var code = String((this.javaClass.getResourceAsStream("/RepositoryImplTemplate.kt").readAllBytes()))
        code = code.replace("BASE_PACKAGE", entity.basePackage)
        code = code.replace("SNAKE_ENTITY", entity.name.toSnakeCase())
        code = code.replace("ENTITY", entity.name)
        code =
            code.replace("FIELD_NAMES_VALUES", entity.fields.joinToString { "${it.name.toSnakeCase()} = :${it.name}" })
        code = code.replace("FIELD_NAMES", entity.fields.joinToString { it.name.toSnakeCase() })
        code = code.replace("FIELD_VALUES", entity.fields.joinToString { ":${it.name}" })
        code = code.replace(
            "                FIELD_MAPPING",
            entity.fields.joinToString(separator = System.lineSeparator()) { "                    \"${it.name}\" to entity.${it.name}," }
        )

        val rowMapperFields = entity.fields.map { field ->
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
        }.joinToString(separator = System.lineSeparator())
        code = code.replace("                ROW_MAPPER_FIELDS", rowMapperFields)
        return code
    }
}