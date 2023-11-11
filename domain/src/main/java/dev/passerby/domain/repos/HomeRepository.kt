package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel

interface HomeRepository {

    suspend fun getCoinsList(): LiveData<CoinModel>
    suspend fun getDate(): LiveData<String>
    suspend fun getFavCoinsList(): LiveData<CoinModel>
    suspend fun getTopCoinsList(): LiveData<CoinModel>

    suspend fun loadCoinsHistory(rank: Int, coinId: String)
    suspend fun loadCoinsList()

    suspend fun searchCoins(coinId: String)
}