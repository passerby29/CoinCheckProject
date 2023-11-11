package dev.passerby.domain.usecases

import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.repos.CoinInfoRepository

class AddCoinToFavUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinModel: CoinModel) = repository.addCoinToFav(coinModel)
}