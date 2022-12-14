package com.example.factchucker.data.repository

import com.example.factchucker.data.remote.ChuckNorrisApi
import com.example.factchucker.data.remote.dto.JokeDto
import com.example.factchucker.domain.repository.JokeRepository
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    private val api: ChuckNorrisApi
) : JokeRepository {
    override suspend fun getRandomJoke(): JokeDto {
        return api.getRandomJoke()
    }

    override suspend fun getJokeByCategory(category: String?): JokeDto {
        return api.getJokeByCategory(category)
    }
}