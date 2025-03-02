package com.attilav.bookstore.services.impl

import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.testAuthorEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceImplTest@Autowired constructor(
    private val underTest: AuthorServiceImpl,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `Test that save persists the Author in the db` () {
        val savedAuthorEntity = underTest.save(testAuthorEntity())
        assertThat(savedAuthorEntity.id).isNotNull()

        val recalledAuthorEntity = authorRepository.findByIdOrNull(savedAuthorEntity.id!!)
        assertThat(recalledAuthorEntity).isNotNull()
        assertThat(recalledAuthorEntity!!).isEqualTo(testAuthorEntity(savedAuthorEntity.id))
    }

    @Test
    fun `Test that the list is an empty list when there are no Authors in the db` () {
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `Test that the list return Authors when there are Authors in the db` () {
        val savedAuthor = authorRepository.save(testAuthorEntity())
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
        val savedAuthor = authorRepository.save(testAuthorEntity())
        val result = underTest.get(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)
    }
}