package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.util.toCamelCase

class ServiceImplGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}ServiceImpl.kt"

    override fun generate(entity: Entity): String {
        var code = String((this.javaClass.getResourceAsStream("/ServiceImplTemplate.kt").readAllBytes()))
        code = code.replace("BASE_PACKAGE", entity.basePackage)
        code = code.replace("CC_ENTITY", entity.name.toCamelCase())
        code = code.replace("ENTITY", entity.name)
        return code
    }
}