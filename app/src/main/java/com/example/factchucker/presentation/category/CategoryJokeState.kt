package com.example.factchucker.presentation.category

import com.example.factchucker.domain.model.Joke

data class CategoryJokeState(
    val isLoading: Boolean = false,
    val joke: Joke = Joke(
        emptyList(),
        "",
        "",
        "",
        ""
    ),
    val error: String = ""
)
