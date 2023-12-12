package dev.passerby.domain.usecases

import dev.passerby.domain.repos.SettingsRepository

class AcceptNewThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(themeId: Int) = repository.acceptNewTheme(themeId)
}