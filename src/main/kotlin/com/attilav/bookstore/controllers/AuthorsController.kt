package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.services.AuthorService
import com.attilav.bookstore.toAuthorDto
import com.attilav.bookstore.toAuthorEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping("/v1/authors")
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        val createdAuthor = authorService.save(authorDto.toAuthorEntity()).toAuthorDto()
        return ResponseEntity(createdAuthor, HttpStatus.CREATED)
    }
}