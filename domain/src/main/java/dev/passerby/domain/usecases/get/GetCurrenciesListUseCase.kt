package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.SettingsRepository

class GetCurrenciesListUseCase(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getCurrenciesList()
}