package dev.passerby.domain.usecases.load

import dev.passerby.domain.repos.HomeRepository

class LoadCoinsHistoryUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.loadCoinsHistory()
}