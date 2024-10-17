package com.test.natife.data.models.base

import com.google.gson.annotations.SerializedName

data class PaginationData(
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int
)