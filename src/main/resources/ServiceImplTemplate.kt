package BASE_PACKAGE.service.impl

import BASE_PACKAGE.model.ENTITYEntity
import BASE_PACKAGE.repository.ENTITYRepository
import BASE_PACKAGE.service.ENTITYService
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class ENTITYServiceImpl(
    private val CC_ENTITYRepository: ENTITYRepository,
) : ENTITYService {

    override fun getAll(): List<ENTITYEntity> {
        logger.info { "Get all ENTITYs ordered by id." }
        return CC_ENTITYRepository.getAllOrderById()
            .also {
                logger.info { "Found: ${it.size}." }
            }
    }

    override fun getById(id: Int): ENTITYEntity {
        logger.info { "Get ENTITY by id = $id." }
        return CC_ENTITYRepository.findById(id)
            ?: throw RuntimeException("ENTITY with id = $id not found!")
    }

    override fun save(entity: ENTITYEntity) {
        logger.info { "Saving ENTITY: $entity." }
        if (entity.id == 0) {
            CC_ENTITYRepository.insert(entity)
        } else {
            CC_ENTITYRepository.update(entity)
        }
    }

    private companion object : KLogging()
}
