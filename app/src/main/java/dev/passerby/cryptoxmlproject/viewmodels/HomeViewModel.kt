package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.passerby.data.repos.HomeRepositoryImpl
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.usecases.SearchCoinsUseCase
import dev.passerby.domain.usecases.get.GetCoinsListUseCase
import dev.passerby.domain.usecases.get.GetDateUseCase
import dev.passerby.domain.usecases.get.GetFavCoinsListUseCase
import dev.passerby.domain.usecases.get.GetTopCoinsListUseCase
import dev.passerby.domain.usecases.load.LoadCoinsHistoryUseCase
import dev.passerby.domain.usecases.load.LoadCoinsListUseCase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HomeRepositoryImpl(application)
    private val getCoinsListUseCase = GetCoinsListUseCase(repository)
    private val getDateUseCase = GetDateUseCase(repository)
    private val getFavCoinsListUseCase = GetFavCoinsListUseCase(repository)
    private val getTopCoinsListUseCase = GetTopCoinsListUseCase(repository)
    private val loadCoinsHistoryUseCase = LoadCoinsHistoryUseCase(repository)
    private val loadCoinsListUseCase = LoadCoinsListUseCase(repository)
    private val searchCoinsUseCase = SearchCoinsUseCase(repository)

    val coinsList = getCoinsListUseCase()
    val currentDate = getDateUseCase()
    val favCoinsList = getFavCoinsListUseCase()
    val topCoinsList = getTopCoinsListUseCase()

    init {
        viewModelScope.launch {
            loadCoinsListUseCase()
//            loadCoinsHistoryUseCase()
        }
    }

    fun searchCoins(coinFilter: String): LiveData<List<CoinModel>> {
        return searchCoinsUseCase(coinFilter)
    }
}