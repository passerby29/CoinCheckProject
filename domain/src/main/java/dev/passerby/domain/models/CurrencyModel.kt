package dev.passerby.domain.models

data class CurrencyModel(
    val id: Int,
    val currencyName: String,
    val currencyCode: String,
    val symbol: String,
    val imageUrl: String,
    val isChecked: Boolean = false
)
