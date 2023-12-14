package dev.passerby.cryptoxmlproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel

class CoinInfoViewModelFactory(
    private val application: Application,
    private val coinId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinInfoViewModel::class.java)) {
            return CoinInfoViewModel(application, coinId) as T
        } else {
            throw RuntimeException("Unknown view model class")
        }
    }
}