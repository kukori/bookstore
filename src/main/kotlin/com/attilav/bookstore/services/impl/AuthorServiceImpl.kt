package com.attilav.bookstore.services.impl

import com.attilav.bookstore.domain.entities.AuthorEntity
import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.services.AuthorService
import org.springframework.stereotype.Service

@Service // this makes sure that the dependencies are injected
class AuthorServiceImpl(private val authorRepository: AuthorRepository): AuthorService {
    override fun save(authorEntity: AuthorEntity): AuthorEntity {
        return authorRepository.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepository.findAll()
    }
}