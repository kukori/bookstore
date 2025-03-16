package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.entities.AuthorEntity
import com.attilav.bookstore.services.AuthorService
import com.attilav.bookstore.testAuthorDto
import com.attilav.bookstore.testAuthorEntityA
import com.attilav.bookstore.testAuthorUpdateRequestDtoA
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

private const val AUTHORS_BASE_URL = "/v1/authors"

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,

    @MockkBean private val authorService: AuthorService
) {

    val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setup() {
        every {
            authorService.create(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `Test that create Author returns a HTTP 201 on a successful create`() {
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDto()
            )
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `Test that create Author returns a HTTP 400 on an error`() {
        every {
            authorService.create(any())
        } throws(IllegalArgumentException())

        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDto()
            )
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `Test that create Author saves the Author`() {
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDto()
            )
        }

        val expected = AuthorEntity(
            id = null,
            name = "John Doe",
            age = 30,
            image = "author-image.jpeg",
            description = "some description"
        )

        verify{ authorService.create(expected) }
    }

    @Test
    fun `Test that list returns an empty list and HTTP 200 if there is no authors in the database`() {
        every {
            authorService.list()
        } answers {
            emptyList()
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `Test that list returns authors and HTTP 200 if there are authors in the database`() {
        every {
            authorService.list()
        } answers {
            listOf(testAuthorEntityA(1))
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", equalTo(1)) }
            content { jsonPath("$[0].name", equalTo("John Doe")) }
            content { jsonPath("$[0].age", equalTo(30)) }
            content { jsonPath("$[0].description", equalTo("some description")) }
            content { jsonPath("$[0].image", equalTo("author-image.jpeg")) }
        }
    }

    @Test
    fun `Test that get returns HTTP 404 if there is no authors in the database with the given id`() {
        every {
            authorService.get(any())
        } answers {
            null
        }

        mockMvc.get("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `Test that get returns HTTP 200 and and AuthorDto if there is an author in the database with the given id`() {
        every {
            authorService.get(any())
        } answers {
            testAuthorEntityA(1)
        }

        mockMvc.get("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(1)) }
            content { jsonPath("$.name", equalTo("John Doe")) }
            content { jsonPath("$.age", equalTo(30)) }
            content { jsonPath("$.description", equalTo("some description")) }
            content { jsonPath("$.image", equalTo("author-image.jpeg")) }
        }
    }

    @Test
    fun `Test that full update Author return HTTP 200 and updated author on successful call`() {
        every {
            authorService.fullUpdate(any(), any())
        } answers {
            secondArg()
        }

        mockMvc.put("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDto(1)
            )
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(1)) }
            content { jsonPath("$.name", equalTo("John Doe")) }
            content { jsonPath("$.age", equalTo(30)) }
            content { jsonPath("$.description", equalTo("some description")) }
            content { jsonPath("$.image", equalTo("author-image.jpeg")) }
        }
    }

    @Test
    fun `Test that full update Author return HTTP 400 on IllegalStateException`() {
        every {
            authorService.fullUpdate(any(), any())
        } throws(IllegalStateException())

        mockMvc.put("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDto(1)
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Test that partial update Author return HTTP 200 and updated author on successful call`() {
        every {
            authorService.partialUpdate(any(), any())
        } answers {
            testAuthorEntityA(999L)
        }

        mockMvc.patch("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorUpdateRequestDtoA(999L)
            )
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(999)) }
            content { jsonPath("$.name", equalTo("John Doe")) }
            content { jsonPath("$.age", equalTo(30)) }
            content { jsonPath("$.description", equalTo("some description")) }
            content { jsonPath("$.image", equalTo("author-image.jpeg")) }
        }
    }

    @Test
    fun `Test that partial update Author return HTTP 400 on IllegalStateException`() {
        every {
            authorService.partialUpdate(any(), any())
        } throws(IllegalStateException())

        mockMvc.patch("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorUpdateRequestDtoA(999L)
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Test that deelte Author returns HTTP 204 on successful delete`() {
        every {
            authorService.delete(any())
        } answers {}

        mockMvc.delete("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorUpdateRequestDtoA(999L)
            )
        }.andExpect {
            status { isNoContent() }
        }
    }
}