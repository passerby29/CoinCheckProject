package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel

interface HomeRepository {

    fun getCoinsList(): LiveData<List<CoinModel>>
    fun getDate(): LiveData<String>
    fun getFavCoinsList(): LiveData<List<FavoriteModel>>
    fun getTopCoinsList(): LiveData<List<CoinModel>>

    suspend fun loadCoinsHistory()
    suspend fun loadCoinsList()

    fun updateFavorites()

    fun searchCoins(coinFilter: String): LiveData<List<CoinModel>>
}