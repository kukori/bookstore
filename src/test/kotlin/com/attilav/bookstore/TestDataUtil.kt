package com.attilav.bookstore

import com.attilav.bookstore.domain.dto.AuthorDto

fun testAuthorDto(id: Long? = null): AuthorDto {
    return AuthorDto(
        id = id,
        name = "John Doe",
        age = 30,
        image = "author-image.jpeg",
        description = "some description"
    )
}