package com.test.natife.di

import android.content.Context
import androidx.room.Room
import com.test.natife.data.database.AppDatabase
import com.test.natife.data.database.GifDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "gifs.db").build()

    @Provides
    @Singleton
    fun provideGifDao(database: AppDatabase): GifDao = database.gifDao()
}