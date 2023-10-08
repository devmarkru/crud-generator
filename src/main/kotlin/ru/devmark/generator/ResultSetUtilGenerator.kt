package ru.devmark.generator

import ru.devmark.meta.Entity

class ResultSetUtilGenerator : CodeGenerator {

    override fun getFileName(entity: Entity): String = "ResultSetUtil.kt"

    override fun generate(entity: Entity): String {
        return String((this.javaClass.getResourceAsStream("/ResultSetUtilTemplate.kt").readAllBytes()))
            .replace("BASE_PACKAGE", entity.basePackage)
    }
}