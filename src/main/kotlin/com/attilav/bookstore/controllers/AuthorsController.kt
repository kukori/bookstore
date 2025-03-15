package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.AuthorUpdateRequest
import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.domain.dto.AuthorUpdateRequestDto
import com.attilav.bookstore.services.AuthorService
import com.attilav.bookstore.toAuthorDto
import com.attilav.bookstore.toAuthorEntity
import com.attilav.bookstore.toAuthorUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/authors")
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        try {
            val createdAuthor = authorService.create(authorDto.toAuthorEntity()).toAuthorDto()
            return ResponseEntity(createdAuthor, HttpStatus.CREATED)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

    @GetMapping
    fun readManyAuthors(): List<AuthorDto> {
        return authorService.list().map {
            it.toAuthorDto()
        }
    }

    @GetMapping(path = ["/{id}"])
    fun readAuthorById(@PathVariable id: Long): ResponseEntity<AuthorDto> {
        val foundAuthor = authorService.get(id)?.toAuthorDto()
        return foundAuthor?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping(path = ["/{id}"])
    fun fullUpdateAuthor(@PathVariable id: Long, @RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        try {
            val updatedAuthor = authorService.fullUpdate(id, authorDto.toAuthorEntity()).toAuthorDto()
            return ResponseEntity(updatedAuthor, HttpStatus.OK)
        } catch (exception: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path = ["/{id}"])
    fun partialUpdateAuthor(@PathVariable id: Long, @RequestBody authorUpdateRequestDto: AuthorUpdateRequestDto): ResponseEntity<AuthorDto> {
        return try {
            val updatedAuthor = authorService.partialUpdate(id, authorUpdateRequestDto.toAuthorUpdateRequest())
            ResponseEntity(updatedAuthor.toAuthorDto(), HttpStatus.OK)
        } catch (exception: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}