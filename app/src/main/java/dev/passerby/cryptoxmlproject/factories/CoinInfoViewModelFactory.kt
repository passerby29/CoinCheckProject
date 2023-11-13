package dev.passerby.cryptoxmlproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel

class CoinInfoViewModelFactory(
    private val application: Application,
    private val rank: Int,
    private val coinId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinInfoViewModel::class.java)) {
            return CoinInfoViewModel(application, rank, coinId) as T
        } else {
            throw RuntimeException("Unknown view model class")
        }
    }
}