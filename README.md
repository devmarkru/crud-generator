# CRUD generator
Утилита позволяет генерировать компоненты для **Spring** (сервис, репозиторий, юнит-тесты) на **Kotlin** для типового проекта, работающего с БД через **JDBC API**.

Пример описания исходной сущности в файле `_Entities.kt`:

```kotlin
package ru.devmark

data class Post(
    val userId: Int,
    val text: String,
    val createdDate: LocalDateTime,
    val background: String?,
)
```

Здесь указывается **базовый пакет** для всего проекта. Поле для id сущности указывать не нужно - он добавится автоматически.

При запуске в первом параметре следует указать папку, где лежит описание исходной сущности.

На выходе получим следующие файлы на **Kotlin**:
* Сущность БД
* Репозиторий
* Сервисный слой (с логированием)
* Юнит-тесты для сервисного слоя

Для каждого компонента уже будут прописаны пакеты согласно схеме:

```
src
└── main
    └── kotlin
        └── ru
            └── devmark
                ├── entity
                ├── repository
                │   └── impl
                ├── service
                │   └──impl
                └── util
```

Проверьте, что в вашем gradle-проекте подключены следующие зависимости:

```kotlin
implementation("org.springframework.boot:spring-boot-starter-jdbc")
implementation("org.springframework.boot:spring-boot-starter-web")
implementation("io.github.microutils:kotlin-logging:3.0.5")

testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
implementation("io.mockk:mockk:1.13.13")

runtimeOnly("org.postgresql:postgresql")
```
