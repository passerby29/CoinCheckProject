package dev.passerby.domain.usecases

import dev.passerby.domain.repos.CoinInfoRepository

class IsCoinAddedToFavUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinId: String) = repository.isCoinAddedToFav(coinId)
}