package com.attilav.bookstore.services.impl

import com.attilav.bookstore.domain.AuthorUpdateRequest
import com.attilav.bookstore.domain.entities.AuthorEntity
import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.testAuthorEntityA
import com.attilav.bookstore.testAuthorEntityB
import com.attilav.bookstore.testAuthorUpdateRequestA
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
            val result = underTest.fullUpdate(123L, testAuthorEntityB(123L))
        }
    }

    @Test
    fun `Test that partial update Author updates the author name` () {
        val newName = "New Name"
        
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(name = newName)
        val updatedAuthorRequest = AuthorUpdateRequest(name = newName)

        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = updatedAuthorRequest
        )
    }

    @Test
    fun `Test that partial update Author updates the author age` () {
        val newAge = 21

        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(age = newAge)
        val updatedAuthorRequest = AuthorUpdateRequest(age = newAge)

        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = updatedAuthorRequest
        )
    }

    @Test
    fun `Test that partial update Author updates the author description` () {
        val newDescription = "new description"

        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(description = newDescription)
        val updatedAuthorRequest = AuthorUpdateRequest(description = newDescription)

        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = updatedAuthorRequest
        )
    }

    @Test
    fun `Test that partial update Author updates the author image` () {
        val newImage = "new_image.jpg"

        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(image = newImage)
        val updatedAuthorRequest = AuthorUpdateRequest(image = newImage)

        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = updatedAuthorRequest
        )
    }

    private fun assertThatAuthorPartialUpdateIsUpdated(
        existingAuthor: AuthorEntity,
        expectedAuthor: AuthorEntity,
        authorUpdateRequest: AuthorUpdateRequest
    ) {
        // Save an existing Author
        val savedExistingAuthor = authorRepository.save(existingAuthor)
        val existingAuthorId = savedExistingAuthor.id!!

        // Update the author
        val updatedAuthor = underTest.partialUpdate(existingAuthorId, authorUpdateRequest)

        // Set up the expected author
        val expected = expectedAuthor.copy(id = updatedAuthor.id!!)
        assertThat(updatedAuthor).isEqualTo(expected)

        val retrievedAuthor = authorRepository.findByIdOrNull(existingAuthorId)
        assertThat(retrievedAuthor).isNotNull()
        assertThat(retrievedAuthor).isEqualTo(expected)
    }

    @Test
    fun `test that partial update does not update the Author when all values are null` () {
        val existingAuthor = authorRepository.save(testAuthorEntityA())
        val updatedAuthor = underTest.partialUpdate(existingAuthor.id!!, AuthorUpdateRequest())
        assertThat(updatedAuthor).isEqualTo(existingAuthor)
    }

    @Test
    fun `Test that partial update fails when Author does not exsist in the db` () {
        assertThrows<IllegalStateException> {
            val result = underTest.partialUpdate(123L, testAuthorUpdateRequestA())
        }
    }

    @Test
    fun `Test that delete Author deletes an existing author in the database` () {
        val existingAuthor = authorRepository.save(testAuthorEntityA())
        underTest.delete(existingAuthor.id!!)
        assertThat(
            authorRepository.existsById(existingAuthor.id!!)
        ).isFalse()
    }

    @Test
    fun `Test that delete Author deletes non existing author in the database` () {
        underTest.delete(999L)
        assertThat(
            authorRepository.existsById(999L)
        ).isFalse()
    }
}