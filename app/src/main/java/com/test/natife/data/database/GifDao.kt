package com.test.natife.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.natife.data.models.base.GifObjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GifDao {
    @Query("SELECT * FROM gifs WHERE isDeleted = 0")
    fun getAllGifs(): Flow<List<GifObjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(gif: GifObjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifs(gifs: List<GifObjectEntity>) // Added function

    @Delete
    suspend fun deleteGif(gif: GifObjectEntity)

    @Query("UPDATE gifs SET isDeleted = 1 WHERE id = :gifId")
    suspend fun markGifAsDeleted(gifId: String)

    @Query("SELECT * FROM gifs WHERE isDeleted = 0")
    fun getPagingSource(): PagingSource<Int, GifObjectEntity>
}
