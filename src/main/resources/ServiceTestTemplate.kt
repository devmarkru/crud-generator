@ExtendWith(MockKExtension::class)
class ENTITYServiceTest {

    @RelaxedMockK
    private lateinit var repository: ENTITYRepository

    @InjectMockKs
    private lateinit var service: ENTITYServiceImpl

    @Test
    fun `When get all then returns list of entity`() {
        val expected = listOf(
            getEntity(123),
            getEntity(456),
        )

        every {
            repository.getAllOrderById()
        } returns expected

        val actual = service.getAll()

        Assertions.assertEquals(expected.size, actual.size)
    }

    @Test
    fun `When get by existing id then returns entity`() {
        val expected = getEntity(123)

        every {
            repository.findById(123)
        } returns expected

        val actual = service.getById(123)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `When get by not existing id then throws exception`() {
        every {
            repository.findById(any())
        } returns null

        Assertions.assertThrows(RuntimeException::class.java) {
            service.getById(321)
        }
    }

    @Test
    fun `When entity id equals zero on save then insert new record`() {
        val entity = getEntity(0)
        service.save(entity)

        verify(exactly = 1) {
            repository.insert(entity)
        }
        verify(exactly = 0) {
            repository.update(any())
        }
    }

    @Test
    fun `When entity id specified on save then update existing record`() {
        val entity = getEntity(123)

        every {
            repository.findById(123)
        } returns entity

        service.save(entity)

        verify(exactly = 0) {
            repository.insert(any())
        }
        verify(exactly = 1) {
            repository.update(entity)
        }
    }

    @Test
    fun `When deleting entity then delete entity by id`() {
        service.deleteById(123)

        verify(exactly = 1) {
            repository.deleteById(123)
        }
    }

    private fun getEntity(id: Int) =
        ENTITYEntity(
            id = id,
            FIELD_VALUES
        )
}
