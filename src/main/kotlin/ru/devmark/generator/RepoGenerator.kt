package ru.devmark.generator

import ru.devmark.meta.Entity
import ru.devmark.meta.FieldType
import ru.devmark.util.toSnakeCase

class RepoGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String =
        "${entity.name}Repository.kt"

    override fun generate(entity: Entity): String {
        var code = String((this.javaClass.getResourceAsStream("/RepositoryTemplate.kt").readAllBytes()))
        code = code.replace("ENTITY", entity.name)
        code = code.replace("BASE_PACKAGE", entity.basePackage)
        return code
    }
}