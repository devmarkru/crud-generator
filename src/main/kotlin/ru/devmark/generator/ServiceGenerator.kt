package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.util.toCamelCase

class ServiceGenerator : KotlinCodeGenerator() {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Service.kt"

    override fun getPackageName(entity: Entity): String =
        "${entity.basePackage}.service"

    override fun getTemplateName(): String = "ServiceTemplate.kt"

    override fun processTemplate(template: String, entity: Entity): String {
        addImports(entity)

        var code = template
        code = code.replace("CC_ENTITY", entity.name.toCamelCase())
        code = code.replace("ENTITY", entity.name)
        return code
    }

    private fun addImports(entity: Entity) {
        import("mu.KLogging")
        import("org.springframework.stereotype.Service")
        import("org.springframework.transaction.annotation.Transactional")

        import("${entity.basePackage}.entity.${entity.name}Entity")
        import("${entity.basePackage}.repository.${entity.name}Repository")
    }
}
