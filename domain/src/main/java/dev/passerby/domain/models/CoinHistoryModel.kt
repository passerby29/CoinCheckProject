package dev.passerby.domain.models

data class CoinHistoryModel(
    val id: Int = 0,
    val coinId: String,
    val prices: List<Double>
)
