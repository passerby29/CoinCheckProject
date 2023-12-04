package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.patrykandpatrick.vico.core.extension.setFieldValue
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

    var selectedCurrency: CurrencyModel? = null

    init {
        currencies.forEach {
            if (it.isChecked) selectedCurrency = it
        }
    }

    fun selectCurrency(currency: CurrencyModel) {
        if (selectedCurrency != currency){
            _isCurrencyChanged.value = true
        }

        currencies.find { it.id == selectedCurrency?.id}?.isChecked = true
        currencies.find { it.id == currency.id}?.isChecked = false
        currencies = getCurrenciesListUseCase()
    }

    fun acceptCurrency(currencyId: Int){
        acceptNewCurrencyUseCase(currencyId)
        currencies = getCurrenciesListUseCase()
    }
}