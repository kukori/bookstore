package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.services.AuthorService
import com.attilav.bookstore.toAuthorDto
import com.attilav.bookstore.toAuthorEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/authors")
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        val createdAuthor = authorService.save(authorDto.toAuthorEntity()).toAuthorDto()
        return ResponseEntity(createdAuthor, HttpStatus.CREATED)
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
}