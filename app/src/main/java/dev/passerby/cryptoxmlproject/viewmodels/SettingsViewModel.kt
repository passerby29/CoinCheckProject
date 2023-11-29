package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.passerby.data.repos.SettingsRepositoryImpl
import dev.passerby.domain.usecases.get.GetCurrenciesListUseCase
import dev.passerby.domain.usecases.get.GetLanguagesListUseCase

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepositoryImpl(application)
    private val getLanguagesListUseCase = GetLanguagesListUseCase(repository)
    private val getCurrenciesListUseCase = GetCurrenciesListUseCase(repository)

    val languages = getLanguagesListUseCase()
    val currencies = getCurrenciesListUseCase()
}