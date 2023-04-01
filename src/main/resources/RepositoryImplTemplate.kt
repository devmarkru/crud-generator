package BASE_PACKAGE.repository.impl

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import BASE_PACKAGE.entity.ENTITYEntity
import BASE_PACKAGE.repository.ENTITYRepository

@Repository
class ENTITYRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : ENTITYRepository {

    override fun getAllOrderById(): List<ENTITYEntity> =
        jdbcTemplate.query(
            "select id, FIELD_NAMES from SNAKE_ENTITY order by id",
            ROW_MAPPER
        )

    override fun findById(id: Int): ENTITYEntity? =
        jdbcTemplate.query(
            "select id, FIELD_NAMES from SNAKE_ENTITY where id = :id",
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun insert(entity: ENTITYEntity) {
        jdbcTemplate.update(
            "insert into SNAKE_ENTITY (FIELD_NAMES) values (FIELD_VALUES)",
            mapOf(
                FIELD_MAPPING
            )
        )
    }

    override fun update(entity: ENTITYEntity) {
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
