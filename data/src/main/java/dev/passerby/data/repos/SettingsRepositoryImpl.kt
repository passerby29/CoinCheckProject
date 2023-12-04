package dev.passerby.data.repos

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.passerby.data.Constants
import dev.passerby.data.Constants.Companion.CURRENCY_ID
import dev.passerby.data.Constants.Companion.LANGUAGE_ID
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel
import dev.passerby.domain.repos.SettingsRepository

class SettingsRepositoryImpl(application: Application) : SettingsRepository {

    private val sharedPreferences = application.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val constants = Constants(application)

    override fun getLanguagesList(): List<LanguageModel> {
        val appLanguageId = sharedPreferences.getInt(LANGUAGE_ID, 0)
        return constants.languagesList.map {
            it.copy(isChecked = it.id == appLanguageId)
        }
    }

    override fun getCurrenciesList(): List<CurrencyModel> {
        val appCurrencyId = sharedPreferences.getInt(CURRENCY_ID, 2)
        return constants.currenciesList.map {
            it.copy(isChecked = it.id == appCurrencyId)
        }
    }

    override fun acceptNewCurrency(currencyId: Int) {
        editor.putInt(CURRENCY_ID, currencyId).apply()
    }
}