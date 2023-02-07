package com.example.factchucker.presentation.category

import com.example.factchucker.MainDispatcherRule
import com.example.factchucker.common.State
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.FakeJokeUseCase
import com.example.factchucker.presentation.joke.JokeState
import com.example.factchucker.presentation.joke.RandomJokeViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryJokeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RandomJokeViewModel
    private lateinit var fakeJokeUseCase: FakeJokeUseCase

    private val randomJoke = Joke(
        listOf("Chuck"),
        "https://api.chucknorris.io/",
        "1",
        "",
        "Chuck Norris sells buckets of food that last for 25 years"
    )

    private val emptyJoke = Joke(
        emptyList(),
        "",
        "",
        "",
        ""
    )

    @Before
    fun setUp() {
        fakeJokeUseCase = FakeJokeUseCase()
        viewModel = RandomJokeViewModel(fakeJokeUseCase)
    }

    @Test
    fun `Success returns correct Joke data`() = runTest {
        fakeJokeUseCase.emit(State.Success(randomJoke))
        Truth.assertThat(viewModel.state.value.joke).isEqualTo(randomJoke)
    }

    @Test
    fun `Error returns Error state with error message`() = runTest {
        fakeJokeUseCase.emit(State.Error("Error!"))
        Truth.assertThat(viewModel.state.value).isEqualTo(JokeState(error = "Error!"))
    }

    @Test
    fun `isLoading is True with default empty data`() = runTest {
        fakeJokeUseCase.emit(State.Loading())
        Truth.assertThat(viewModel.state.value.isLoading).isTrue()
        Truth.assertThat(viewModel.state.value.joke).isEqualTo(emptyJoke)
    }
}
