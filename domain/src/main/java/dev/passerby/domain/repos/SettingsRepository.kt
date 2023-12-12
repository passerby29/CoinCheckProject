package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel

interface SettingsRepository {
    fun getLanguagesList(): List<LanguageModel>
    fun getCurrenciesList(): List<CurrencyModel>
    fun getSelectedThemeId(): LiveData<Int>

    fun acceptNewLanguage(languageId: Int)
    fun acceptNewCurrency(currencyId: Int)
    fun acceptNewTheme(themeId: Int)
}