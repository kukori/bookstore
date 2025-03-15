package com.attilav.bookstore

import com.attilav.bookstore.domain.AuthorUpdateRequest
import com.attilav.bookstore.domain.dto.AuthorDto
import com.attilav.bookstore.domain.dto.AuthorUpdateRequestDto
import com.attilav.bookstore.domain.entities.AuthorEntity

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
