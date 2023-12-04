package dev.passerby.domain.usecases

import dev.passerby.domain.repos.SettingsRepository

class AcceptNewCurrencyUseCase(private val repository: SettingsRepository) {
    operator fun invoke(currencyId: Int) = repository.acceptNewCurrency(currencyId)
}