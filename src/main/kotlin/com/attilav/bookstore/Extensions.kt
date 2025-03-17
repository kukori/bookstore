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
import com.attilav.bookstore.exceptions.InvalidAuthorException

fun AuthorEntity.toAuthorDto(): AuthorDto {
    return AuthorDto(
        id = this.id,
        name = this.name,
        age = this.age,
        description = this.description,
        image = this.image
    )
}

fun AuthorDto.toAuthorEntity() = AuthorEntity(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorUpdateRequestDto.toAuthorUpdateRequest() = AuthorUpdateRequest(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun BookSummary.toBookEntity(authorEntity: AuthorEntity) = BookEntity(
    isbn = this.isbn,
    title =this.title,
    description = this.description,
    image = this.image,
    authorEntity = authorEntity
)

fun AuthorSummaryDto.toAuthorSummary() = AuthorSummary(
    id = this.id,
    name = this.name,
    image = this.image,
)

fun BookSummaryDto.toBookSummary() = BookSummary(
    isbn = isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = this.author.toAuthorSummary()
)

fun AuthorEntity.toAuthorSummaryDto(): AuthorSummaryDto {
    val authorId = this.id ?: throw InvalidAuthorException()
    return AuthorSummaryDto(
        id = authorId,
        name = this.name,
        image = this.image,
    )
}

fun BookEntity.toBookSummaryDto() = BookSummaryDto(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = this.authorEntity.toAuthorSummaryDto()
)