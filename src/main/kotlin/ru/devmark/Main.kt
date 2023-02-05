package ru.devmark

import ru.devmark.generator.EntityGenerator
import ru.devmark.generator.RepoGenerator
import ru.devmark.generator.RepoImplGenerator
import ru.devmark.generator.ServiceGenerator
import ru.devmark.generator.ServiceImplGenerator
import ru.devmark.generator.ServiceTestGenerator
import ru.devmark.meta.EntityParser
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please, specify path to the file entity.txt with / at the end.")
        return
    }
    val basePath = args[0]
    val lines = Files.readAllLines(Path.of(basePath + "entity.txt"))
    println("Parsing entity description.")
    val entity = EntityParser().parse(lines)
    val generators = listOf(
        EntityGenerator(),
        RepoGenerator(),
        RepoImplGenerator(),
        ServiceGenerator(),
        ServiceImplGenerator(),
        ServiceTestGenerator(),
    )
    println("Begin source files generation")
    generators.forEach { generator ->
        Files.writeString(Path.of(basePath + generator.getFileName(entity)), generator.generate(entity))
    }
    println("All source files generated!")
}
