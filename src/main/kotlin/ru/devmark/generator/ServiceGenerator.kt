package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.util.toCamelCase

class ServiceGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Service.kt"

    override fun generate(entity: Entity): String {
        var code = String((this.javaClass.getResourceAsStream("/ServiceTemplate.kt").readAllBytes()))
        code = code.replace("CC_ENTITY", entity.name.toCamelCase())
        code = code.replace("ENTITY", entity.name)
        return code
    }
}