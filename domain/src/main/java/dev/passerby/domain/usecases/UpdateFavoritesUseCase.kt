package dev.passerby.domain.usecases

import dev.passerby.domain.repos.HomeRepository

class UpdateFavoritesUseCase(private val repository: HomeRepository) {
    operator fun invoke() = repository.updateFavorites()
}