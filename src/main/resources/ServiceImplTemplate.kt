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

    override fun findById(id: Int): ENTITYEntity? {
        logger.info { "Find ENTITY by id = $id." }
        return CC_ENTITYRepository.findById(id)
    }

    override fun getById(id: Int): ENTITYEntity {
        logger.info { "Get ENTITY by id = $id." }
        return CC_ENTITYRepository.findById(id)
            ?: throw RuntimeException("ENTITY with id = $id not found!")
    }

    @Transactional
    override fun save(entity: ENTITYEntity): Int {
        logger.info { "Saving ENTITY: $entity." }
        return if (entity.id == 0) {
            CC_ENTITYRepository.insert(entity)
        } else {
            getById(entity.id)
            CC_ENTITYRepository.update(entity)
            entity.id
        }
    }

    override fun deleteById(id: Int) {
        logger.info { "Deleting ENTITY by id = $id." }
        CC_ENTITYRepository.deleteById(id)
    }

    private companion object : KLogging()
}
