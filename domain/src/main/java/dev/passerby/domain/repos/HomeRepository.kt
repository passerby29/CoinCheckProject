package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel

interface HomeRepository {

    suspend fun getCoinsList(): LiveData<List<CoinModel>>
    suspend fun getDate(): LiveData<String>
    suspend fun getFavCoinsList(): LiveData<List<CoinModel>>
    suspend fun getTopCoinsList(): LiveData<List<CoinModel>>

    suspend fun loadCoinsHistory(rank: Int, coinId: String)
    suspend fun loadCoinsList()

    suspend fun searchCoins(coinFilter: String): LiveData<List<CoinModel>>
}