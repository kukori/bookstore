package com.attilav.bookstore.services.impl

import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.testAuthorEntityA
import com.attilav.bookstore.testAuthorEntityB
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceImplTest@Autowired constructor(
    private val underTest: AuthorServiceImpl,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `Test that the list is an empty list when there are no Authors in the db` () {
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `Test that save persists the Author in the db` () {
        val savedAuthorEntity = underTest.create(testAuthorEntityA())
        assertThat(savedAuthorEntity.id).isNotNull()

        val recalledAuthorEntity = authorRepository.findByIdOrNull(savedAuthorEntity.id!!)
        assertThat(recalledAuthorEntity).isNotNull()
        assertThat(recalledAuthorEntity!!).isEqualTo(testAuthorEntityA(savedAuthorEntity.id))
    }

    @Test
    fun `Test that an author with an id throws an IllegalArgumentException` () {
        assertThrows<IllegalArgumentException> {
            val existingAuthorEntity = testAuthorEntityA(999)
            underTest.create(existingAuthorEntity)
        }
    }

    @Test
    fun `Test that the list return Authors when there are Authors in the db` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val expected = listOf(savedAuthor)
        val result = underTest.list()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Test that get returns null when the Author is not present in the db` () {
        val result = underTest.get(9999)
        assertThat(result).isNull()
    }

    @Test
    fun `Test that get returns an author when it is present in the db` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val result = underTest.get(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)
    }

    @Test
    fun `Test that full update successfully updates the Author in the db` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val savedAuthorId = savedAuthor.id!!
        val updatedAuthor = testAuthorEntityB(savedAuthorId)
        val result = underTest.fullUpdate(savedAuthorId, updatedAuthor)
        assertThat(result).isEqualTo(updatedAuthor)

        val retreatedAuthor = authorRepository.findByIdOrNull(savedAuthorId)
        assertThat(retreatedAuthor).isNotNull()
        assertThat(retreatedAuthor).isEqualTo(updatedAuthor)
    }

    @Test
    fun `Test that full update fails when Author does not exsist in the db` () {
        assertThrows<IllegalStateException> {
            val result = underTest.fullUpdate(123L, testAuthorEntityB())
        }
    }
}