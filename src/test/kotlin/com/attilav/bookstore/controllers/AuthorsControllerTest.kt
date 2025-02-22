package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.dto.AuthorDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    val objectMapper = jacksonObjectMapper()

    @Test
    fun `Test that create Author returns a HTTP 201 on a successful create`() {
        mockMvc.post("/v1/authors") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                AuthorDto(
                    id = null,
                    name = "John Doe",
                    age = 30,
                    image = "author-image.jpeg",
                    description = "some description"
                )
            )
        }.andExpect { status { isCreated() } }
    }

}