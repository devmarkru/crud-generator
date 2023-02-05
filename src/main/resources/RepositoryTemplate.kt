package BASE_PACKAGE.repository

import BASE_PACKAGE.model.ENTITYEntity

interface ENTITYRepository {

    fun getAllOrderById(): List<ENTITYEntity>

    fun findById(id: Int): ENTITYEntity?

    fun insert(entity: ENTITYEntity)

    fun update(entity: ENTITYEntity)
}
