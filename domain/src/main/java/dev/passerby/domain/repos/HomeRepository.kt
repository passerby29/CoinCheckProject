package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel

interface HomeRepository {

    fun getCoinsList(): LiveData<List<CoinModel>>
    fun getDate(): LiveData<Array<Int>>
    fun getFavCoinsList(): LiveData<List<FavoriteModel>>
    fun getTopCoinsList(): LiveData<List<CoinModel>>
    fun getCurrencyId(): Int

    suspend fun loadCoinsHistory()
    suspend fun loadCoinsList()

    fun searchCoins(coinFilter: String): LiveData<List<CoinModel>>
}