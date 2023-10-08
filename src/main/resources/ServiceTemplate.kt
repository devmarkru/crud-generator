package BASE_PACKAGE.service

import BASE_PACKAGE.entity.ENTITYEntity

interface ENTITYService {

    fun getAll(): List<ENTITYEntity>

    fun findById(id: Int): ENTITYEntity?

    fun getById(id: Int): ENTITYEntity

    fun save(entity: ENTITYEntity): Int
}
