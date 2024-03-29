# CRUD generator
Утилита позволяет генерировать компоненты (сервисный слой, репозиторий и т.д.) для типового **Spring**-проекта, работающего с БД через **JDBC API**.

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

Здесь пакет указывается базовый для всего проекта. Поле для id сущности указывать не нужно - он добавится автоматически. На выходе все файлы будут распределены в разные дочерние пакеты.

При запуске в первом параметре следует указать папку, где лежит описание исходной сущности.

На выходе получим следующие файлы на **Kotlin**:
* Сущность БД
* Репозиторий
* Сервисный слой (с логированием)
* Юнит-тесты для сервисного слоя

В целевой проект также надо будет добавить следующие зависимости:

```kotlin
implementation("io.github.microutils:kotlin-logging:1.12.5")
testImplementation("io.mockk:mockk:1.13.4")
```
