package com.test.natife.data.models.net

import com.google.gson.annotations.SerializedName

data class PaginationNet(
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int
)