@Repository
class ENTITYRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {

    fun getAllOrderById(): List<ENTITYEntity> =
        jdbcTemplate.query(
            """
                select
                    id,
                    FIELD_NAMES
                from SNAKE_ENTITY
                order by id
            """.trimIndent(),
            ROW_MAPPER
        )

    fun findById(id: Int): ENTITYEntity? =
        jdbcTemplate.query(
            """
                select
                    id, 
                    FIELD_NAMES 
                from SNAKE_ENTITY
                where id = :id
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    fun insert(entity: ENTITYEntity): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            """
                insert into SNAKE_ENTITY (
                    FIELD_NAMES
                ) values (
                    FIELD_VALUES
                )
            """.trimIndent(),
            MapSqlParameterSource(
                mapOf(
                    FIELD_MAPPING
                )
            ),
            keyHolder,
            arrayOf("id"),
        )
        return keyHolder.key as Int
    }

    fun update(entity: ENTITYEntity) {
        jdbcTemplate.update(
            """
                update SNAKE_ENTITY set
                    FIELD_NAMES_VALUES
                where id = :id
            """.trimIndent(),
            MapSqlParameterSource(
                mapOf(
                    "id" to entity.id,
                    FIELD_MAPPING
                )
            )
        )
    }

    fun deleteById(id: Int) {
        jdbcTemplate.update(
            """
                delete from SNAKE_ENTITY
                where id = :id
            """.trimIndent(),
            mapOf("id" to id)
        )
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
