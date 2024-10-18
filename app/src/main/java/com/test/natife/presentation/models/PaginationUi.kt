package com.test.natife.presentation.models

import com.google.gson.annotations.SerializedName

data class PaginationUi(
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int
)