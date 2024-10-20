package ru.devmark.generator

import ru.devmark.meta.Entity
import java.util.SortedSet
import java.util.TreeSet

abstract class KotlinCodeGenerator : CodeGenerator {

    private val imports: SortedSet<String> = TreeSet()

    protected fun import(import: String) {
        imports += import
    }

    protected abstract fun getPackageName(entity: Entity): String

    protected abstract fun getTemplateName(): String?

    protected abstract fun processTemplate(template: String, entity: Entity): String

    final override fun generate(entity: Entity): String {
        var template = getTemplateName()
            ?.let { templateName ->
                String(
                    (this.javaClass.getResourceAsStream("/$templateName")?.readAllBytes())
                        ?: throw RuntimeException("Template not found: $templateName.")
                )
            } ?: ""

        template = processTemplate(template, entity)

        val lines = mutableListOf<String>()
        lines += "package ${getPackageName(entity)}"
        lines += ""

        if (imports.isNotEmpty()) {
            lines.addAll(imports.map { "import $it" })
            lines += ""
        }

        lines += template
        return lines.joinToString(separator = System.lineSeparator())
    }
}
