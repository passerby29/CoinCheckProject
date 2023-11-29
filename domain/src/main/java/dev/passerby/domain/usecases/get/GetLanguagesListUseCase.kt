package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.SettingsRepository

class GetLanguagesListUseCase(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getLanguagesList()
}