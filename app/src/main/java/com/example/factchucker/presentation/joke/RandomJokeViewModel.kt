package com.example.factchucker.presentation.joke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factchucker.common.State.*
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.GetJokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val getJokeUseCase: GetJokeUseCase
) : ViewModel() {

    private val emptyJoke = Joke(
        emptyList(),
        "",
        "",
        "",
        ""
    )

    // Mutable _state exposed to viewmodel, Immutable state exposed to composables (clean code, separation of concerns)
    private var _state = mutableStateOf(JokeState())
    var state: State<JokeState> = _state

    init {
        getJoke()
    }

    fun getJoke() {
        viewModelScope.launch {
            getJokeUseCase(null).collect { result ->
                when (result) {
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
            }
        }
    }
}
