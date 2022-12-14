package com.example.factchucker.domain.model

data class Joke(
    val categories: List<Any>,
    val iconUrl: String,
    val id: String,
    val url: String,
    val value: String
)
