package com.test.natife.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.natife.data.models.base.GifObjectEntity

@Database(entities = [GifObjectEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}