package com.attilav.bookstore.services

import com.attilav.bookstore.domain.BookSummary
import com.attilav.bookstore.domain.entities.BookEntity

interface BookService {
    fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean>

    fun list(): List<BookEntity>
}