package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.passerby.data.repos.CoinInfoRepositoryImpl
import dev.passerby.domain.models.CoinHistoryModel
import dev.passerby.domain.models.FavoriteModel
import dev.passerby.domain.usecases.AddCoinToFavUseCase
import dev.passerby.domain.usecases.IsCoinAddedToFavUseCase
import dev.passerby.domain.usecases.RemoveCoinFromFavUseCase
import dev.passerby.domain.usecases.get.GetCoinHistoryUseCase
import dev.passerby.domain.usecases.get.GetCoinInfoUseCase
import dev.passerby.domain.usecases.load.LoadCoinHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinInfoViewModel(
    application: Application,
    private val coinId: String
) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)
    private val getCoinHistoryUseCase = GetCoinHistoryUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val isCoinAddedToFavUseCase = IsCoinAddedToFavUseCase(repository)
    private val loadCoinHistory = LoadCoinHistoryUseCase(repository)
    private val addCoinToFavUseCase = AddCoinToFavUseCase(repository)
    private val removeCoinFromFavUseCase = RemoveCoinFromFavUseCase(repository)

    val coinHistory = getCoinHistoryUseCase(coinId)
    val coinInfo = getCoinInfoUseCase(coinId)

    suspend fun loadCoinHistory(period: String): LiveData<CoinHistoryModel>? {
        return loadCoinHistory(coinId, period)
    }

    fun addCoinToFav(favoriteModel: FavoriteModel) = viewModelScope.launch {
        addCoinToFavUseCase(favoriteModel)
    }

    fun removeCoinFromFav(coinId: String) = viewModelScope.launch {
        removeCoinFromFavUseCase(coinId)
    }

    suspend fun isCoinAddedToFav(): Boolean {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                isCoinAddedToFavUseCase(coinId)
            }
        }
    }
}