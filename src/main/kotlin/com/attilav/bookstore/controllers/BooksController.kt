package com.attilav.bookstore.controllers

import com.attilav.bookstore.domain.dto.BookSummaryDto
import com.attilav.bookstore.exceptions.InvalidAuthorException
import com.attilav.bookstore.services.BookService
import com.attilav.bookstore.toBookSummary
import com.attilav.bookstore.toBookSummaryDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BooksController(private val bookService: BookService) {

    @PutMapping(path = ["/v1/books/{isbn}"])
    fun createBook(@PathVariable isbn: String, @RequestBody bookSummaryDto: BookSummaryDto): ResponseEntity<BookSummaryDto> {
        try {
            val (savedBook, isCreated) = bookService.createUpdate(isbn, bookSummaryDto.toBookSummary())
            val responseCode = if(isCreated) HttpStatus.CREATED else HttpStatus.OK
            return ResponseEntity(savedBook.toBookSummaryDto(), responseCode)
        } catch (exception: InvalidAuthorException) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (exception: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(path = ["/v1/books"])
    fun readManyBooks(): List<BookSummaryDto> {
        return bookService.list().map { it.toBookSummaryDto() }
    }
}