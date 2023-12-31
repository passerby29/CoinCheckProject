package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.passerby.data.repos.SettingsRepositoryImpl
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel
import dev.passerby.domain.usecases.AcceptNewCurrencyUseCase
import dev.passerby.domain.usecases.AcceptNewLanguageUseCase
import dev.passerby.domain.usecases.AcceptNewThemeUseCase
import dev.passerby.domain.usecases.get.GetCurrenciesListUseCase
import dev.passerby.domain.usecases.get.GetLanguagesListUseCase
import dev.passerby.domain.usecases.get.GetSelectedThemeIdUseCase

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepositoryImpl(application)
    private val getLanguagesListUseCase = GetLanguagesListUseCase(repository)
    private val getCurrenciesListUseCase = GetCurrenciesListUseCase(repository)
    private val getSelectedThemeIdUseCase = GetSelectedThemeIdUseCase(repository)
    private val acceptNewCurrencyUseCase = AcceptNewCurrencyUseCase(repository)
    private val acceptNewLanguageUseCase = AcceptNewLanguageUseCase(repository)
    private val acceptNewThemeUseCase = AcceptNewThemeUseCase(repository)

    var languages = getLanguagesListUseCase()
    var currencies = getCurrenciesListUseCase()
    var selectedThemeId = getSelectedThemeIdUseCase()

    private val _isLanguageChanged = MutableLiveData<Boolean>()
    val isLanguageChanged: LiveData<Boolean>
        get() = _isLanguageChanged

    private val _currentLanguage = MutableLiveData<LanguageModel>()
    val currentLanguage: LiveData<LanguageModel>
        get() = _currentLanguage

    private val _isCurrencyChanged = MutableLiveData<Boolean>()
    val isCurrencyChanged: LiveData<Boolean>
        get() = _isCurrencyChanged

    private val _currentCurrency = MutableLiveData<CurrencyModel>()
    val currentCurrency: LiveData<CurrencyModel>
        get() = _currentCurrency
    
    init {
        languages.forEach {
            if (it.isChecked) _currentLanguage.value = it
        }

        currencies.forEach {
            if (it.isChecked) _currentCurrency.value = it
        }
    }

    fun selectLanguage(language: LanguageModel) {
        if (currentLanguage.value != language){
            _isLanguageChanged.value = true
        }

        languages = getLanguagesListUseCase()
    }

    fun selectCurrency(currency: CurrencyModel) {
        if (currentCurrency.value != currency){
            _isCurrencyChanged.value = true
        }

        currencies = getCurrenciesListUseCase()
    }

    fun acceptCurrency(currencyId: Int){
        acceptNewCurrencyUseCase(currencyId)
        currencies = getCurrenciesListUseCase()
        _currentCurrency.value = currencies[currencyId]
    }

    fun acceptLanguage(languageId: Int){
        acceptNewLanguageUseCase(languageId)
        languages = getLanguagesListUseCase()
        _currentLanguage.value = languages[languageId]
    }

    fun acceptTheme(themeId: Int){
        acceptNewThemeUseCase(themeId)
        selectedThemeId = getSelectedThemeIdUseCase()
    }

    fun resetCurrency(){
        _isCurrencyChanged.value = false
    }

    fun resetLanguage(){
        _isLanguageChanged.value = false
    }
}