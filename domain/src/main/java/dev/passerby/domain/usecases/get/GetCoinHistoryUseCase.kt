package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.CoinInfoRepository

class GetCoinHistoryUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinId: String) = repository.getCoinHistory(coinId)
}