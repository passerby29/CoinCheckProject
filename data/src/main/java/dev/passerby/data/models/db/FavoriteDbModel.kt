package dev.passerby.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteDbModel(
    val icon: String,
    val id: String,
    val name: String,
    val price: Double,
    val priceChange1h: Double,
    @PrimaryKey
    val rank: Int,
    val symbol: String,
)