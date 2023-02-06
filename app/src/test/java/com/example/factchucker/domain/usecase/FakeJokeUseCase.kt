package com.example.factchucker.domain.usecase

import com.example.factchucker.common.State
import com.example.factchucker.domain.model.Joke
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeJokeUseCase : GetJokeUseCase {

    private val fakeFlow = MutableSharedFlow<State<Joke>>()

    suspend fun emit(value: State<Joke>) = fakeFlow.emit(value)

    override fun invoke(category: String?) = fakeFlow
}
