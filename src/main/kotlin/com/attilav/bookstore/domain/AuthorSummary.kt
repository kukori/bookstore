package com.attilav.bookstore.domain

data class AuthorSummary (
    val id: Long,
    val name: String? = null,
    val image: String? = null
)