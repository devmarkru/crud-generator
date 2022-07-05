package ru.devmark.meta

class EntityParser {

    fun parse(sourceLines: List<String>): Entity {
        var entityName = ""
        val fields = mutableListOf<Field>()
        for (line in sourceLines) {
            if (line.isNotBlank()) {
                val parts = line.trim().split("\\s+".toRegex())
                if (parts[0] == "data" && parts[1] == "class") {
                    entityName = parts[2]
                    if (entityName.endsWith("(")) {
                        entityName = entityName.substring(0, entityName.length - 1)
                    }
                } else if (parts.first() in setOf("val", "var")) {
                    fields += getField(parts)
                }
            }
        }
        return Entity(
            name = entityName,
            fields = fields,
        )
    }

    private fun getField(parts: List<String>): Field {
        var name = parts[1]
        if (name.endsWith(":")) {
            name = name.substring(0, name.length - 1)
        }
        val typeText = parts[2]
        val nullable = typeText.endsWith("?")
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