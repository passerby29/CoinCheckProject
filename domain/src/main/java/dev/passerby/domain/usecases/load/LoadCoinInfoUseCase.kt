package dev.passerby.domain.usecases.load

import dev.passerby.domain.repos.CoinInfoRepository

class LoadCoinInfoUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinId: String) = repository.loadCoinInfo(coinId)
}