package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.passerby.data.repos.SettingsRepositoryImpl
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.usecases.AcceptNewCurrencyUseCase
import dev.passerby.domain.usecases.get.GetCurrenciesListUseCase
import dev.passerby.domain.usecases.get.GetLanguagesListUseCase

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepositoryImpl(application)
    private val getLanguagesListUseCase = GetLanguagesListUseCase(repository)
    private val getCurrenciesListUseCase = GetCurrenciesListUseCase(repository)
    private val acceptNewCurrencyUseCase = AcceptNewCurrencyUseCase(repository)

    val languages = getLanguagesListUseCase()
    var currencies = getCurrenciesListUseCase()

    private val _isCurrencyChanged = MutableLiveData<Boolean>()
    val isCurrencyChanged: LiveData<Boolean>
        get() = _isCurrencyChanged

    private val _currentCurrency = MutableLiveData<CurrencyModel>()
    val currentCurrency: LiveData<CurrencyModel>
        get() = _currentCurrency
    
    init {
        currencies.forEach {
            if (it.isChecked) _currentCurrency.value = it
        }
    }

    fun selectCurrency(currency: CurrencyModel) {
        if (currentCurrency.value != currency){
            _isCurrencyChanged.value = true
        }

        currencies.find { it.id == currentCurrency.value?.id}?.isChecked = true
        currencies.find { it.id == currency.id}?.isChecked = false
        currencies = getCurrenciesListUseCase()
    }

    fun acceptCurrency(currencyId: Int){
        acceptNewCurrencyUseCase(currencyId)
        currencies = getCurrenciesListUseCase()
        _currentCurrency.value = currencies[currencyId]
    }

    fun resetCurrency(){
        _isCurrencyChanged.value = false
    }
}