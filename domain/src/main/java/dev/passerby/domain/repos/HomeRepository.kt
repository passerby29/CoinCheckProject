package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel

interface HomeRepository {

    fun getCoinsList(): LiveData<List<CoinModel>>
    fun getDate(): LiveData<String>
    fun getFavCoinsList(): LiveData<List<CoinModel>>
    fun getTopCoinsList(): LiveData<List<CoinModel>>

    suspend fun loadCoinsHistory(rank: Int, coinId: String)
    suspend fun loadCoinsList()

    fun searchCoins(coinFilter: String): LiveData<List<CoinModel>>
}