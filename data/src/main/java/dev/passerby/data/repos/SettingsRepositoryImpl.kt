package dev.passerby.data.repos

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.passerby.data.Constants
import dev.passerby.data.Constants.Companion.CURRENCY_ID
import dev.passerby.data.Constants.Companion.LANGUAGE_ID
import dev.passerby.data.Constants.Companion.THEME_ID
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel
import dev.passerby.domain.repos.SettingsRepository

class SettingsRepositoryImpl(application: Application) : SettingsRepository {

    private val sharedPreferences = application.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val appLanguageId = sharedPreferences.getInt(LANGUAGE_ID, 0)
    private val appCurrencyId = sharedPreferences.getInt(CURRENCY_ID, 0)
    private var constants = Constants(application, appLanguageId)

    override fun getLanguagesList(): List<LanguageModel> {
        return constants.languagesList.map {
            it.copy(isChecked = it.id == appLanguageId)
        }
    }

    override fun getCurrenciesList(): List<CurrencyModel> {
        return constants.currenciesList.map {
            it.copy(isChecked = it.id == appCurrencyId)
        }
    }

    override fun getSelectedThemeId(): LiveData<Int> {
        val themeIdLiveData = MutableLiveData<Int>()
        themeIdLiveData.value = sharedPreferences.getInt(THEME_ID, 0)
        return themeIdLiveData
    }

    override fun acceptNewLanguage(languageId: Int) {
        editor.putInt(LANGUAGE_ID, languageId).apply()
    }

    override fun acceptNewCurrency(currencyId: Int) {
        editor.putInt(CURRENCY_ID, currencyId).apply()
    }

    override fun acceptNewTheme(themeId: Int) {
        editor.putInt(THEME_ID, themeId).apply()
    }
}