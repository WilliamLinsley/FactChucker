package com.example.factchucker.domain.usecase.random

import com.example.factchucker.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
class GetJokeUseCaseRandomImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

}
