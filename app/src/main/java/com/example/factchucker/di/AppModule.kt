package com.example.factchucker.di

import com.example.factchucker.common.Constants
import com.example.factchucker.data.remote.ChuckNorrisApi
import com.example.factchucker.data.repository.JokeRepositoryImpl
import com.example.factchucker.domain.repository.JokeRepository
import com.example.factchucker.domain.usecase.GetJokeUseCase
import com.example.factchucker.domain.usecase.random.GetJokeUseCaseRandomImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideChuckNorrisApi(): ChuckNorrisApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChuckNorrisApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJokeRepository(api: ChuckNorrisApi): JokeRepository {
        return JokeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetJokeUseCase(repo: JokeRepository): GetJokeUseCase {
        return GetJokeUseCaseRandomImpl(repo)
    }
}
