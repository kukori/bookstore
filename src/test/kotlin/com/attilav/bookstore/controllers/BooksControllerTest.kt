package com.attilav.bookstore.controllers

import com.attilav.bookstore.*
import com.attilav.bookstore.services.BookService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.StatusResultMatchersDsl
import java.lang.IllegalStateException

private const val BOOKS_BASE_URL = "/v1/books"

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val bookService: BookService
) {

    val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setup() {
    }

    @Test
    fun `Test that the createFullUpdateBook returns http 201 when book is created`() {
        assertThatUserCreatedUpdated(true) { isCreated() }
    }

    @Test
    fun `Test that the createFullUpdateBook returns http 200 when book is updated`() {
        assertThatUserCreatedUpdated(false) { isOk() }
    }

    private fun assertThatUserCreatedUpdated(isCreated: Boolean, statusCodeAssertion: StatusResultMatchersDsl.() -> Unit) {
        val isbn = "978-089-230342-0777"
        val author = testAuthorEntityA(1L)
        val savedBook = testBookEntityA(isbn, author)

        val authorSummaryDto = testAuthorSummaryDtoA(1L)
        val bookSummaryDto = testBookSummaryDtoA(isbn, authorSummaryDto)

        every {
            bookService.createUpdate(any(), any())
        } answers {
            Pair(savedBook, isCreated)
        }

        mockMvc.put("/v1/books/${isbn}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                bookSummaryDto
            )
        }.andExpect { status { statusCodeAssertion } }
    }

    @Test
    fun `Test that the createFullUpdateBook returns http 500 when the author does not have an id`() {
        val isbn = "978-089-230342-0777"
        val author = testAuthorEntityA()
        val savedBook = testBookEntityA(isbn, author)

        val authorSummaryDto = testAuthorSummaryDtoA(1L)
        val bookSummaryDto = testBookSummaryDtoA(isbn, authorSummaryDto)

        every {
            bookService.createUpdate(any(), any())
        } answers {
            Pair(savedBook, true)
        }

        mockMvc.put("/v1/books/${isbn}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                bookSummaryDto
            )
        }.andExpect { status { isInternalServerError() } }
    }

    @Test
    fun `Test that the createFullUpdateBook returns http 400 when the author does not exists in the database`() {
        val isbn = "978-089-230342-0777"

        val authorSummaryDto = testAuthorSummaryDtoA(999L)
        val bookSummaryDto = testBookSummaryDtoA(isbn, authorSummaryDto)

        every {
            bookService.createUpdate(any(), any())
        } throws IllegalStateException()

        mockMvc.put("/v1/books/${isbn}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                bookSummaryDto
            )
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `Test that readManyBooks returns a list of all books`() {
        val isbn = "978-089-230342-0777"

        every {
            bookService.list()
        } answers {
            listOf(testBookEntityA(isbn = isbn, testAuthorEntityA(id=1)))
        }

        mockMvc.get("/v1/books") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].isbn", equalTo(isbn)) }
            content { jsonPath("$[0].title", equalTo("Test Book A")) }
            content { jsonPath("$[0].image", equalTo("book-image.jpeg")) }
            content { jsonPath("$[0].author.id", equalTo(1)) }
            content { jsonPath("$[0].author.name", equalTo("John Doe")) }
            content { jsonPath("$[0].author.image", equalTo( "author-image.jpeg")) }
        }
    }
}