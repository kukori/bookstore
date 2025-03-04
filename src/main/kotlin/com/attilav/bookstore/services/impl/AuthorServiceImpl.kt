package com.attilav.bookstore.services.impl

import com.attilav.bookstore.domain.entities.AuthorEntity
import com.attilav.bookstore.repositories.AuthorRepository
import com.attilav.bookstore.services.AuthorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service // this makes sure that the dependencies are injected
class AuthorServiceImpl(private val authorRepository: AuthorRepository): AuthorService {
    override fun create(authorEntity: AuthorEntity): AuthorEntity {
        require(authorEntity.id == null)
        return authorRepository.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

    override fun get(id: Long): AuthorEntity? {
        return authorRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity {
        check(authorRepository.existsById(id))
        val normalizedAuthor = authorEntity.copy(id=id)
        return authorRepository.save(normalizedAuthor)
    }
}