package com.attilav.bookstore.services

import com.attilav.bookstore.domain.entities.AuthorEntity

interface AuthorService {
    fun save(authorEntity: AuthorEntity): AuthorEntity
}