package dev.passerby.domain.usecases

import dev.passerby.domain.repos.HomeRepository

class SearchCoinsUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(coinId: String) = repository.searchCoins(coinId)
}