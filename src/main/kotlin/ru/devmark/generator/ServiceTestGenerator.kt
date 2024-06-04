package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType

class ServiceTestGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}ServiceTest.kt"

    override fun generate(entity: Entity): String {
        var code = String((this.javaClass.getResourceAsStream("/ServiceTestTemplate.kt").readAllBytes()))
        code = code.replace("BASE_PACKAGE", entity.basePackage)
        code = code.replace("ENTITY", entity.name)
        val rowMapperFields = entity.fields.mapIndexed { index, field ->
            val value = when (field.type) {
                FieldType.INT -> "${field.name} = ${100 * index + 1},"
                FieldType.LONG -> "${field.name} = ${100 * index + 1}L,"
                FieldType.STRING -> "${field.name} = \"${field.name}\","
                FieldType.BOOL -> "${field.name} = true,"
                FieldType.BIG_DECIMAL -> "${field.name} = BigDecimal(\"${100 * index + 1}.00\"),"
                FieldType.DATE_TIME -> "${field.name} = LocalDateTime.now(),"
            }
            "            $value"
        }.joinToString(separator = System.lineSeparator())
        code = code.replace("            FIELD_VALUES", rowMapperFields)
        return code
    }
}