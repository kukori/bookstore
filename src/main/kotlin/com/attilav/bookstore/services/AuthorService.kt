package com.attilav.bookstore.services

import com.attilav.bookstore.domain.entities.AuthorEntity

interface AuthorService {
    fun create(authorEntity: AuthorEntity): AuthorEntity

    fun list(): List<AuthorEntity>

    fun get(id: Long): AuthorEntity?

    fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity
}