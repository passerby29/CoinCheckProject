package dev.passerby.data.models.dto.info


import com.google.gson.annotations.SerializedName

data class CoinsListDto(
    @SerializedName("meta")
    val meta: MetaDto,
    @SerializedName("result")
    val coinsList: List<CoinDto>
)