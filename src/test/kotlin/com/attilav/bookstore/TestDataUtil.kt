package com.attilav.bookstore

import com.attilav.bookstore.domain.AuthorSummary
import com.attilav.bookstore.domain.AuthorUpdateRequest
import com.attilav.bookstore.domain.BookSummary
import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.domain.dto.AuthorSummaryDto
import com.attilav.bookstore.domain.dto.AuthorUpdateRequestDto
import com.attilav.bookstore.domain.dto.BookSummaryDto
import com.attilav.bookstore.domain.entities.AuthorEntity
import com.attilav.bookstore.domain.entities.BookEntity

const val BOOK_A_ISBN = "978-089-230342-0777"

fun testAuthorDto(id: Long? = null): AuthorDto {
    return AuthorDto(
        id = id,
        name = "John Doe",
        age = 30,
        image = "author-image.jpeg",
        description = "some description"
    )
}

fun testAuthorEntityA(id: Long? = null): AuthorEntity {
    return AuthorEntity(
        id = id,
        name = "John Doe",
        age = 30,
        image = "author-image.jpeg",
        description = "some description"
    )
}

fun testAuthorEntityB(id: Long? = null): AuthorEntity {
    return AuthorEntity(
        id = id,
        name = "Updated Author",
        age = 22,
        description = "Updated Author",
        image = "http://localhost:8080/image"
    )
}

fun testAuthorUpdateRequestDtoA(id: Long? = null): AuthorUpdateRequestDto {
    return  AuthorUpdateRequestDto(
        id = id,
        name ="John Doe",
        image = "author-image.jpeg",
        description = "some description",
        age = 30,
    )
}

fun testAuthorUpdateRequestA(id: Long? = null): AuthorUpdateRequest {
    return  AuthorUpdateRequest(
        id = id,
        name ="John Doe",
        image = "author-image.jpeg",
        description = "some description",
        age = 30,
    )
}

fun testAuthorSummaryDtoA(id: Long): AuthorSummaryDto {
    return AuthorSummaryDto(
        id = id,
        name ="John Doe",
        image = "author-image.jpeg",
    )
}

fun testAuthorSummaryA(id: Long): AuthorSummary {
    return AuthorSummary(
        id = id,
        name ="John Doe",
        image = "author-image.jpeg",
    )
}

fun testBookEntityA(isbn: String, author: AuthorEntity): BookEntity {
    return BookEntity(
        isbn = isbn,
        title = "Test Book A",
        description = "A test book",
        image = "book-image.jpeg",
        authorEntity = author,
    )
}

fun testBookSummaryDtoA(isbn: String, author: AuthorSummaryDto): BookSummaryDto {
    return BookSummaryDto(
        isbn = isbn,
        title = "Test Book A",
        description = "A test book",
        image = "book-image.jpeg",
        author = author,
    )
}

fun testBookSummaryA(isbn: String, author: AuthorSummary): BookSummary {
    return BookSummary(
        isbn = isbn,
        title = "Test Book A",
        description = "A test book",
        image = "book-image.jpeg",
        author = author,
    )
}

fun testBookSummaryB(isbn: String, author: AuthorSummary): BookSummary {
    return BookSummary(
        isbn = isbn,
        title = "Test Book B",
        description = "B test book",
        image = "book-image-b.jpeg",
        author = author,
    )
}