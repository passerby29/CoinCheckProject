package dev.passerby.domain.usecases.load

import dev.passerby.domain.repos.CoinInfoRepository

class LoadCoinHistoryUseCase(private val repository: CoinInfoRepository) {
    suspend operator fun invoke(coinId: String, period: String) =
        repository.loadCoinHistory(coinId, period)
}