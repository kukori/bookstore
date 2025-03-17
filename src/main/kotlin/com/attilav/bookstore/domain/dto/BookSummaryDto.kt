package com.attilav.bookstore.domain.dto

class BookSummaryDto (
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author: AuthorSummaryDto
)