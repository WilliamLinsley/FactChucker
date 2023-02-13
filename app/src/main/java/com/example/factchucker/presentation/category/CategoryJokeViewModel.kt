package com.example.factchucker.presentation.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factchucker.common.Constants.CATEGORY_PARAM
import com.example.factchucker.common.State.*
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.GetJokeUseCase
import com.example.factchucker.presentation.JokeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryJokeViewModel @Inject constructor(
    private val getJokeUseCase: GetJokeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val emptyJoke = Joke(
        emptyList(),
        "",
        "",
        "",
        ""
    )

    // Mutable _state exposed to viewmodel, Immutable state exposed to composables (clean code, separation of concerns)
    private val _state = mutableStateOf(JokeState())
    val state: State<JokeState> = _state

    init {
        val category: String = checkNotNull(savedStateHandle[CATEGORY_PARAM])
        getJoke(category)
    }

    fun getJoke(category: String? = "") {
        getJokeUseCase(category).onEach { result ->
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
        }.launchIn(viewModelScope)
    }
}
