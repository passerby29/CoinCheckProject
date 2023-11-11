package dev.passerby.domain.usecases.load

import dev.passerby.domain.repos.HomeRepository

class LoadCoinsListUseCase(private val repository: HomeRepository){
    suspend operator fun invoke() = repository.loadCoinsList()
}