plugins {
    application
    kotlin("jvm") version "1.9.25"
}

group = "ru.devmark"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass.set("ru.devmark.MainKt")
}
