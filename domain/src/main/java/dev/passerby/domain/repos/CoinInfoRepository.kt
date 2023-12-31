package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinHistoryModel
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel

interface CoinInfoRepository {

    fun getCoinHistory(coinId: String): LiveData<CoinHistoryModel>
    fun getCoinInfo(coinId: String): LiveData<CoinModel>

    suspend fun loadCoinHistory(coinId: String, period: String): LiveData<CoinHistoryModel>?

    suspend fun addCoinToFav(favoriteModel: FavoriteModel)
    suspend fun removeCoinFromFav(coinId: String)
    suspend fun isCoinAddedToFav(coinId: String): Boolean
}