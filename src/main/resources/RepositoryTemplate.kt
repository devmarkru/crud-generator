package BASE_PACKAGE.repository

import BASE_PACKAGE.entity.ENTITYEntity

interface ENTITYRepository {

    fun getAllOrderById(): List<ENTITYEntity>

    fun findById(id: Int): ENTITYEntity?

    fun insert(entity: ENTITYEntity): Int

    fun update(entity: ENTITYEntity)

    fun deleteById(id: Int)
}
