package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.CoinInfoRepository

class GetCoinInfoUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinId: String) = repository.getCoinInfo(coinId)
}