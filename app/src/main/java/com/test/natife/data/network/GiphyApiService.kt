package com.test.natife.data.network

import com.test.natife.data.models.net.GiphyResponseNet
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiService {
    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GiphyResponseNet
}
