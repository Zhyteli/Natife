package com.test.natife.di

import com.test.natife.data.database.GifDao
import com.test.natife.data.network.GiphyApiService
import com.test.natife.data.repositories.GiphyRepositoryImpl
import com.test.natife.domain.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGiphyRepository(
        apiService: GiphyApiService,
        gifDao: GifDao,
        @ApiKey apiKey: String
    ): GiphyRepository = GiphyRepositoryImpl(apiService, gifDao, apiKey)
}