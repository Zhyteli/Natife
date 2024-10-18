package com.test.natife.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = "YGHnKKBGSydS6nSt6WAoUcICWwmgCfvL"
}
