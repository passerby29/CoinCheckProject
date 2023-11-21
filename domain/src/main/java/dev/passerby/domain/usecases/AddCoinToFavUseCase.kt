package dev.passerby.domain.usecases

import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel
import dev.passerby.domain.repos.CoinInfoRepository

class AddCoinToFavUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(favoriteModel: FavoriteModel) = repository.addCoinToFav(favoriteModel)
}