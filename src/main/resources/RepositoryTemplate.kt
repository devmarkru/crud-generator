import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ENTITYRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {

    fun getAllOrderById(): List<ENTITYEntity> =
        jdbcTemplate.query(
            "select id, FIELD_NAMES from SNAKE_ENTITY order by id",
            ROW_MAPPER
        )

    fun findById(id: Int): ENTITYEntity? =
        jdbcTemplate.query(
            "select id, FIELD_NAMES from SNAKE_ENTITY where id = :id",
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    fun insert(entity: ENTITYEntity) {
        jdbcTemplate.update(
            "insert into SNAKE_ENTITY (FIELD_NAMES) values (FIELD_VALUES)",
            mapOf(
                FIELD_MAPPING
            )
        )
    }

    fun update(entity: ENTITYEntity) {
        jdbcTemplate.update(
            "update SNAKE_ENTITY set FIELD_NAMES_VALUES where id = :id",
            mapOf(
                "id" to entity.id,
                FIELD_MAPPING
            )
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
