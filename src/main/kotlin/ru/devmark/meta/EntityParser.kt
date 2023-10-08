package ru.devmark.meta

class EntityParser {

    fun parse(sourceLines: List<String>): List<Entity> {
        val entities = mutableMapOf<String, Entity>()
        var entityName = ""
        var basePackage = ""
        for (line in sourceLines) {
            if (line.isNotBlank()) {
                val parts = line.trim().split("\\s+".toRegex())
                when {
                    parts.first() == "package" -> basePackage = parts[1]
                    parts.first() == "data" && parts[1] == "class" -> {
                        entityName = parts[2]
                        if (entityName.endsWith("(")) {
                            entityName = entityName.substring(0, entityName.length - 1)
                        }
                    }

                    parts.first() in setOf("val", "var") -> {
                        entities
                            .getOrPut(entityName) { Entity(basePackage = basePackage, name = entityName) }
                            .fields += getField(parts)
                    }

                    parts.first() == ")" -> entityName = ""
                }
            }
        }
        return entities.values.toList()
    }

    private fun getField(parts: List<String>): Field {
        var name = parts[1]
        if (name.endsWith(":")) {
            name = name.substring(0, name.length - 1)
        }
        val typeText = parts[2]
        val nullable = typeText.contains("?")
        val type = FieldType.values()
            .firstOrNull { typeText.startsWith(it.kotlinType) }
            ?: throw RuntimeException("Unknown field type: $typeText")
        return Field(
            name = name,
            type = type,
            nullable = nullable,
        )
    }
}