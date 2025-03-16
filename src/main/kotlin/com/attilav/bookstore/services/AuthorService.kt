package com.attilav.bookstore.services

import com.attilav.bookstore.domain.AuthorUpdateRequest
import com.attilav.bookstore.domain.entities.AuthorEntity
import org.springframework.http.ResponseEntity

interface AuthorService {
    fun create(authorEntity: AuthorEntity): AuthorEntity

    fun list(): List<AuthorEntity>

    fun get(id: Long): AuthorEntity?

    fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity

    fun partialUpdate(id: Long, authorUpdate: AuthorUpdateRequest): AuthorEntity

    fun delete(id: Long)
}