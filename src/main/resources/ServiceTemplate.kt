import org.springframework.stereotype.Service

@Service
class ENTITYService(
    private val CC_ENTITYRepository: ENTITYRepository,
) {

    fun getAll(): List<ENTITYEntity> =
        CC_ENTITYRepository.getAllOrderById()

    fun getById(id: Int): ENTITYEntity =
        CC_ENTITYRepository.findById(id)
            ?: throw RuntimeException("ENTITY with id = $id not found!")

    fun save(entity: ENTITYEntity) {
        if (entity.id == 0) {
            CC_ENTITYRepository.insert(entity)
        } else {
            CC_ENTITYRepository.update(entity)
        }
    }
}
