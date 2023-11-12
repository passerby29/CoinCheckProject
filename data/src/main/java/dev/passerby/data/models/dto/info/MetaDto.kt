package dev.passerby.data.models.dto.info


import com.google.gson.annotations.SerializedName

data class MetaDto(
    @SerializedName("hasNextPage")
    val hasNextPage: Boolean,
    @SerializedName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerializedName("itemCount")
    val itemCount: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pageCount")
    val pageCount: Int
)