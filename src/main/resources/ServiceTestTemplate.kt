package BASE_PACKAGE.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import BASE_PACKAGE.model.ENTITYEntity
import BASE_PACKAGE.repository.ENTITYRepository
import java.time.LocalDateTime

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
        service.save(entity)

        verify(exactly = 0) {
            repository.insert(any())
        }
        verify(exactly = 1) {
            repository.update(entity)
        }
    }

    private fun getEntity(id: Int) =
        PostEntity(
            id = id,
            FIELD_VALUES
        )
}
