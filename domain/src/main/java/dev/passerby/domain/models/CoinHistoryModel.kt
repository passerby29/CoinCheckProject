package dev.passerby.domain.models

data class CoinHistoryModel(
    val id: Int,
    val coinId: String,
    val prices: List<Double>
)
