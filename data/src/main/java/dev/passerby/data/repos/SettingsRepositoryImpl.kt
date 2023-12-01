package dev.passerby.data.repos

import android.app.Application
import android.content.Context.MODE_PRIVATE
import dev.passerby.data.CURRENCY_ID
import dev.passerby.data.LANGUAGE_ID
import dev.passerby.data.currenciesList
import dev.passerby.data.languagesList
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel
import dev.passerby.domain.repos.SettingsRepository

class SettingsRepositoryImpl(application: Application) : SettingsRepository {

    private val sharedPreferences = application.getSharedPreferences("AppPreferences", MODE_PRIVATE)

    override fun getLanguagesList(): List<LanguageModel> {
        val appLanguageId = sharedPreferences.getInt(LANGUAGE_ID, 0)
        return languagesList.map {
            it.copy(isChecked = it.id == appLanguageId)
        }
    }

    override fun getCurrenciesList(): List<CurrencyModel> {
        val appCurrencyId = sharedPreferences.getInt(CURRENCY_ID, 0)
        return currenciesList.map {
            it.copy(isChecked = it.id == appCurrencyId)
        }
    }
}