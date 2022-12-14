package com.example.factchucker.domain.repository

import com.example.factchucker.data.remote.dto.JokeDto

interface JokeRepository {

    suspend fun getRandomJoke(): JokeDto

    suspend fun getJokeByCategory(category: String?): JokeDto
}