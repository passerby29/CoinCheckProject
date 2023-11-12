package dev.passerby.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coinsHistory")
data class CoinHistoryDbModel(
    @PrimaryKey
    val id: Int,
    val coinId: String,
    val prices: List<Double>
)
