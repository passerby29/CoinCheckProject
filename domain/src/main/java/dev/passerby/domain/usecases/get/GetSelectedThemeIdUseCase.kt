package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.SettingsRepository

class GetSelectedThemeIdUseCase(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getSelectedThemeId()
}