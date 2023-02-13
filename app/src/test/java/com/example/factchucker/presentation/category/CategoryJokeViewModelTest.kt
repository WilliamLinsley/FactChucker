package com.example.factchucker.presentation.category

import androidx.lifecycle.SavedStateHandle
import com.example.factchucker.MainDispatcherRule
import com.example.factchucker.common.Constants.CATEGORY_PARAM
import com.example.factchucker.common.State
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.domain.usecase.FakeJokeUseCase
import com.example.factchucker.presentation.JokeState
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryJokeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CategoryJokeViewModel
    private lateinit var fakeJokeUseCase: FakeJokeUseCase
    private lateinit var savedStateHandle: SavedStateHandle

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
        savedStateHandle = SavedStateHandle().apply {
            set(CATEGORY_PARAM, "animal")
        }
        viewModel = CategoryJokeViewModel(fakeJokeUseCase, savedStateHandle)
    }

    @Test
    fun `Success returns correct Joke data`() = runTest {
        fakeJokeUseCase.emit(State.Success(randomJoke))
        Truth.assertThat(viewModel.state.value.joke).isEqualTo(randomJoke)
    }

    @Test
    fun `Success with null data returns empty Joke data`() = runTest {
        fakeJokeUseCase.emit(State.Success(null))
        Truth.assertThat(viewModel.state.value.joke).isEqualTo(emptyJoke)
    }

    @Test
    fun `Error returns Error state with error message`() = runTest {
        fakeJokeUseCase.emit(State.Error("Error!"))
        Truth.assertThat(viewModel.state.value).isEqualTo(JokeState(error = "Error!"))
    }

    @Test
    fun `Error with null message returns Error state with 'An error has occurred'`() = runTest {
        fakeJokeUseCase.emit(State.Error(message = null))
        Truth.assertThat(viewModel.state.value).isEqualTo(JokeState(error = "An error has occurred"))
    }

    @Test
    fun `isLoading is True with default empty data`() = runTest {
        fakeJokeUseCase.emit(State.Loading())
        Truth.assertThat(viewModel.state.value.isLoading).isTrue()
        Truth.assertThat(viewModel.state.value.joke).isEqualTo(emptyJoke)
    }

    @Test
    fun `No Category throws NPE`() = runTest {
        assertThrows(IllegalStateException::class.java) {
            savedStateHandle = SavedStateHandle().apply {
                set(CATEGORY_PARAM, null)
            }
            viewModel = CategoryJokeViewModel(fakeJokeUseCase, savedStateHandle)
        }
    }
}
