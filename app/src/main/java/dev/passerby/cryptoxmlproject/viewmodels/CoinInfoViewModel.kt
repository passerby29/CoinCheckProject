package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.passerby.data.repos.CoinInfoRepositoryImpl
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel
import dev.passerby.domain.usecases.AddCoinToFavUseCase
import dev.passerby.domain.usecases.RemoveCoinFromFavUseCase
import dev.passerby.domain.usecases.get.GetCoinHistoryUseCase
import dev.passerby.domain.usecases.get.GetCoinInfoUseCase
import dev.passerby.domain.usecases.load.LoadCoinHistoryUseCase
import kotlinx.coroutines.launch

class CoinInfoViewModel(
    application: Application,
    private val rank: Int,
    private val coinId: String
) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)
    private val getCoinHistoryUseCase = GetCoinHistoryUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadCoinHistory = LoadCoinHistoryUseCase(repository)
    private val addCoinToFavUseCase = AddCoinToFavUseCase(repository)
    private val removeCoinFromFavUseCase = RemoveCoinFromFavUseCase(repository)

    val coinHistory = getCoinHistoryUseCase(coinId)
    val coinInfo = getCoinInfoUseCase(coinId)

    fun loadCoinHistory(period: String) = viewModelScope.launch {
        loadCoinHistory(coinId, period)
    }

    fun addCoinToFav(favoriteModel: FavoriteModel) = viewModelScope.launch {
        addCoinToFavUseCase(favoriteModel)
    }

    fun removeCoinFromFav() = viewModelScope.launch {
        removeCoinFromFavUseCase(coinId)
    }
}