package com.example.factchucker.data.repository

import com.example.factchucker.data.remote.ChuckNorrisApi
import com.example.factchucker.data.remote.dto.JokeDto
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.repository.JokeRepository
import javax.inject.Inject

class FakeJokeRepository : JokeRepository {

    private val emptyJoke: JokeDto = JokeDto(emptyList(), "", "", "", "", "", "")

    override suspend fun getRandomJoke(): JokeDto {
        return emptyJoke
    }

    override suspend fun getJokeByCategory(category: String?): JokeDto {
        return emptyJoke
    }
}