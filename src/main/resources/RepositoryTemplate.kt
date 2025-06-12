@Repository
class ENTITYRepository(
    private val jdbcClient: JdbcClient,
) {

    fun getAllOrderById(): List<ENTITYEntity> =
        jdbcClient
            .sql(
                """
                    select
                        id,
                        FIELD_NAMES
                    from SNAKE_ENTITY
                    order by id
                """.trimIndent()
            )
            .query(ROW_MAPPER)
            .list()

    fun findById(id: Int): ENTITYEntity? =
        jdbcClient
            .sql(
                """
                    select
                        id, 
                        FIELD_NAMES 
                    from SNAKE_ENTITY
                    where id = :id
                """.trimIndent()
            )
            .param("id", id)
            .query(ROW_MAPPER)
            .list()
            .firstOrNull()

    fun insert(entity: ENTITYEntity): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient
            .sql(
                """
                    insert into SNAKE_ENTITY (
                        FIELD_NAMES
                    ) values (
                        FIELD_VALUES
                    )
                """.trimIndent()
            )
            .params(
                mapOf(
                    FIELD_MAPPING
                )
            )
            .update(keyHolder, "id")
        return keyHolder.key as Int
    }

    fun update(entity: ENTITYEntity) {
        jdbcClient
            .sql(
                """
                    update SNAKE_ENTITY set
                        FIELD_NAMES_VALUES
                    where id = :id
                """.trimIndent()
            )
            .params(
                mapOf(
                    "id" to entity.id,
                    FIELD_MAPPING
                )
            )
            .update()
    }

    fun deleteById(id: Int) {
        jdbcClient
            .sql("delete from SNAKE_ENTITY where id = :id")
            .param("id", id)
            .update()
    }

    private companion object {
        val ROW_MAPPER = RowMapper { rs, _ ->
            ENTITYEntity(
                id = rs.getInt("id"),
                ROW_MAPPER_FIELDS
            )
        }
    }
}
