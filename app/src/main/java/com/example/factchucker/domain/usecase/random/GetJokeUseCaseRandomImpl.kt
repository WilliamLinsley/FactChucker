package com.example.factchucker.domain.usecase.random

import com.example.factchucker.common.State
import com.example.factchucker.data.remote.dto.toJoke
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.repository.JokeRepository
import com.example.factchucker.domain.usecase.GetJokeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class GetJokeUseCaseRandomImpl @Inject constructor(
    private val repository: JokeRepository
) : GetJokeUseCase {
    override fun invoke(category: String?): Flow<State<Joke>> = flow {
        try {
            emit(State.Loading())
            val joke = repository.getRandomJoke().toJoke()
            emit(State.Success(joke))
        } catch (e: HttpException) {
            emit(State.Error(e.localizedMessage ?: "An error has occurred"))
        } catch (e: IOException) {
            emit(State.Error("Server is unreachable, check connection: ${e.localizedMessage ?: "IOException"}"))
        } catch (e: TimeoutException) {
            emit(State.Error("Timeout error: ${e.localizedMessage ?: "Timeout Exception"}"))
        }
    }
}
