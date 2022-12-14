package com.example.factchucker.domain.usecase.random

import com.example.factchucker.common.State
import com.example.factchucker.data.remote.dto.toJoke
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomJokeUseCase @Inject constructor(
    private val repository: JokeRepository
) {
    operator fun invoke(): Flow<State<Joke>> = flow {
        try {
            emit(State.Loading())
            val joke = repository.getRandomJoke().toJoke()
            emit(State.Success(joke))
        } catch (e: HttpException) {
            emit(State.Error(e.localizedMessage ?: "An error has occurred"))
        } catch (e: IOException) {
            emit(State.Error("Server is unreachable, check connection"))
        }
    }
}