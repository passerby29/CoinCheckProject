package dev.passerby.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteDbModel(
    val availableSupply: Long,
    val contractAddress: String?,
    val decimals: Int?,
    val exp: List<String>,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceBtc: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    @PrimaryKey
    val rank: Int,
    val redditUrl: String?,
    val symbol: String,
    val totalSupply: Long,
    val twitterUrl: String?,
    val volume: Double,
    val websiteUrl: String,
)