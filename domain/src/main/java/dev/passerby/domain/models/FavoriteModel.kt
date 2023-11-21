package dev.passerby.domain.models

data class FavoriteModel(
    val icon: String,
    val id: String,
    val name: String,
    val price: Double,
    val priceChange1h: Double,
    val rank: Int,
    val symbol: String,
)