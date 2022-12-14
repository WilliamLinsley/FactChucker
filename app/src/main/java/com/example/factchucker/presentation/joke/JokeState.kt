package com.example.factchucker.presentation.joke

import com.example.factchucker.domain.model.Joke

data class JokeState(
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
