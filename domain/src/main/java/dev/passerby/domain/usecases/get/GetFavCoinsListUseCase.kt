package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.HomeRepository

class GetFavCoinsListUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.getFavCoinsList()
}