package com.test.natife.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.natife.domain.models.GifObject

//class GiphyPagingSource(
//    private val apiService: GiphyApiService,
//    private val apiKey: String,
//    private val query: String
//) : PagingSource<Int, GifObject>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifObject> {
//        val position = params.key ?: 0
//        val loadSize = minOf(params.loadSize, 50) // Giphy API max limit is 50
//
//        return try {
//            Log.d("PagingSource", "load() called with position: $position, loadSize: $loadSize")
//
//            val response = apiService.searchGifs(
//                apiKey = apiKey,
//                query = query,
//                limit = loadSize,
//                offset = position
//            )
//
//            val totalCount = response.pagination.totalCount
//            Log.d("PagingSource", "Total available items: $totalCount")
//
//            val nextKey = if (position + loadSize >= totalCount) null else position + loadSize
//
//            Log.d("PagingSource", "Loaded ${response.data.size} items. NextKey: $nextKey")
//
//            LoadResult.Page(
//                data = response.data,
//                prevKey = if (position == 0) null else position - loadSize,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            Log.e("PagingSource", "Error loading data", e)
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, GifObject>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.nextKey?.minus(state.config.pageSize)
//                ?: anchorPage?.prevKey?.plus(state.config.pageSize)
//        }
//    }
//}


