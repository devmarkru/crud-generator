package ru.devmark

import ru.devmark.generator.*
import ru.devmark.meta.EntityParser
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please, specify path to the file entities.txt with / at the end.")
        return
    }
    val basePath = args[0]
    val lines = Files.readAllLines(Path.of(basePath + "entities.txt"))
    println("Parsing entity description.")
    val entities = EntityParser().parse(lines)
    val generators = listOf(
        EntityGenerator(),
        RepoGenerator(),
        RepoImplGenerator(),
        ServiceGenerator(),
        ServiceImplGenerator(),
        ServiceTestGenerator(),
    )
    println("Begin source files generation")
    entities.forEach { entity ->
        generators.forEach { generator ->
            Files.writeString(Path.of(basePath + generator.getFileName(entity)), generator.generate(entity))
        }
    }

    entities.firstOrNull { entity ->
        entity.fields.any { field -> field.nullable }
    }
        ?.let { entity ->
            val generator = ResultSetUtilGenerator()
            Files.writeString(Path.of(basePath + generator.getFileName(entity)), generator.generate(entity))
        }
    println("All source files generated!")
}
