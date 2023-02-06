package com.example.factchucker.domain.usecase

import com.example.factchucker.common.State
import com.example.factchucker.domain.model.Joke
import kotlinx.coroutines.flow.Flow

interface GetJokeUseCase {

    operator fun invoke(category: String?): Flow<State<Joke>>
}
