package dev.passerby.domain.usecases

import dev.passerby.domain.repos.HomeRepository

class SearchCoinsUseCase(private val repository: HomeRepository) {
    operator fun invoke(coinFilter: String) = repository.searchCoins(coinFilter)
}