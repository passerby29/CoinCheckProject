package dev.passerby.domain.repos

import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel

interface SettingsRepository {
    fun getLanguagesList(): List<LanguageModel>

    fun getCurrenciesList(): List<CurrencyModel>
}