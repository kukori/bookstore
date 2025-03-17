package com.attilav.bookstore.services.impl

import com.attilav.bookstore.domain.BookSummary
import com.attilav.bookstore.domain.entities.BookEntity
import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.repositories.BookRepository
import com.attilav.bookstore.services.BookService
import com.attilav.bookstore.toBookEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
): BookService {

    @Transactional
    override fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean> {
        val normalisedBook = bookSummary.copy(isbn = isbn)
        val isExists = bookRepository.existsById(isbn)

        val author = authorRepository.findByIdOrNull(normalisedBook.author.id)
        checkNotNull(author)

        val savedBook = bookRepository.save(normalisedBook.toBookEntity(author))
        return Pair(savedBook, !isExists)
    }
}