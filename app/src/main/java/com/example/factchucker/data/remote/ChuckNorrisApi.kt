package com.example.factchucker.data.remote

import com.example.factchucker.data.remote.dto.JokeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChuckNorrisApi {

    @GET("/jokes/random")
    suspend fun getRandomJoke(): JokeDto

    @GET("/jokes/random")
    suspend fun getJokeByCategory(@Query("category") category: String?): JokeDto
}