package dev.passerby.domain.usecases

import dev.passerby.domain.repos.SettingsRepository

class AcceptNewLanguageUseCase(private val repository: SettingsRepository) {
    operator fun invoke(languageId: Int) = repository.acceptNewLanguage(languageId)
}