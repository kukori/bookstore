package com.attilav.bookstore

import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.domain.entities.AuthorEntity

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