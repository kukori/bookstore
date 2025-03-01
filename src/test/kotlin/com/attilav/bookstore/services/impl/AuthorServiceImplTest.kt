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

}