package dev.passerby.domain.usecases.get

import dev.passerby.domain.repos.HomeRepository

class GetDateUseCase(private val repository: HomeRepository) {
    operator fun invoke() = repository.getDate()
}