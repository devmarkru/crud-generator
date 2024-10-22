package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType

class ServiceTestGenerator : KotlinCodeGenerator() {

    override fun getFileName(entity: Entity): String =
        "${entity.name}ServiceTest.kt"

    override fun getPackageName(entity: Entity): String =
        "${entity.basePackage}.service"

    override fun getTemplateName(): String = "ServiceTestTemplate.kt"

    override fun processTemplate(template: String, entity: Entity): String {
        addImports(entity)

        var code = template
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

    private fun addImports(entity: Entity) {
        entity.fields
            .mapNotNull { it.type.requiredImport }
            .forEach { import(it) }

        import("io.mockk.every")
        import("io.mockk.impl.annotations.InjectMockKs")
        import("io.mockk.impl.annotations.RelaxedMockK")
        import("io.mockk.junit5.MockKExtension")
        import("io.mockk.verify")
        import("java.math.BigDecimal")
        import("java.time.LocalDateTime")
        import("org.junit.jupiter.api.Assertions")
        import("org.junit.jupiter.api.Test")
        import("org.junit.jupiter.api.extension.ExtendWith")

        import("${entity.basePackage}.entity.${entity.name}Entity")
        import("${entity.basePackage}.repository.${entity.name}Repository")
    }
}
