package com.example.factchucker.presentation.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factchucker.common.State.*
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.category.GetJokeUseCaseCategoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryJokeViewModel @Inject constructor(
    private val getJokeUseCaseCategoryImpl: GetJokeUseCaseCategoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val emptyJoke = Joke(emptyList(), "", "", "", "")

    // Mutable state exposed to viewmodel (clean code, separation of concerns)
    private val _state = mutableStateOf(CategoryJokeState())

    // Immutable state exposed to composables (clean code, separation of concerns)
    val state: State<CategoryJokeState> = _state

    init {
        val category: String? = checkNotNull(savedStateHandle["category"])
        getJoke(category)
    }

    fun getJoke(category: String? = "") {
        getJokeUseCaseCategoryImpl(category).onEach { result ->
            when (result) {
                is Success -> {
                    _state.value = CategoryJokeState(
                        joke = result.data ?: emptyJoke
                    )
                }
                is Error -> {
                    _state.value = CategoryJokeState(
                        error = result.message ?: "An error has occurred"
                    )
                }
                is Loading -> {
                    _state.value = CategoryJokeState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
