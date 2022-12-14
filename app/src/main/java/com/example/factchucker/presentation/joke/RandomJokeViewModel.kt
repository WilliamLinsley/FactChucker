package com.example.factchucker.presentation.joke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factchucker.common.State.*
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.random.GetRandomJokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val getRandomJokeUseCase: GetRandomJokeUseCase
) : ViewModel() {

    private val emptyJoke = Joke(emptyList(),"","","", "")

    //Mutable state exposed to viewmodel (clean code, separation of concerns)
    private val _state = mutableStateOf(JokeState())
    //Immutable state exposed to composables (clean code, separation of concerns)
    val state: State<JokeState> = _state

    init {
        getJoke()
    }

    fun getJoke() {
        getRandomJokeUseCase().onEach { result ->
            when(result) {
                is Success -> {
                    _state.value = JokeState(
                        joke = result.data ?: emptyJoke
                    )
                }
                is Error -> {
                    _state.value = JokeState(
                        error = result.message ?: "An error has occurred"
                    )
                }
                is Loading -> {
                    _state.value = JokeState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}