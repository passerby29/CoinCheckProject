package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinHistoryModel
import dev.passerby.domain.models.CoinModel

interface CoinInfoRepository {

    suspend fun getCoinHistory(coinId: String): LiveData<CoinHistoryModel>
    suspend fun getCoinInfo(coinId: String): LiveData<CoinModel>

    suspend fun loadCoinHistory(coinId: String, period: String): LiveData<CoinHistoryModel>?

    suspend fun addCoinToFav(coinModel: CoinModel)
    suspend fun removeCoinFromFav(coinId: String)
}