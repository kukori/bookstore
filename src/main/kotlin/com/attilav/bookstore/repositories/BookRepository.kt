package com.attilav.bookstore.repositories

import com.attilav.bookstore.domain.entities.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<BookEntity, String>