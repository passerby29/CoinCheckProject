package dev.passerby.data

import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel

val languagesList = arrayListOf(
    LanguageModel(0, "English", "English"),
    LanguageModel(1, "English", "English"),
    LanguageModel(2, "English", "English"),
    LanguageModel(3, "English", "English"),
    LanguageModel(4, "English", "English"),
)

val currenciesList = arrayListOf(
    CurrencyModel(0, "United States Dollar", "USD", "\u0024", ""),
    CurrencyModel(1, "United States Dollar", "USD", "\u0024", ""),
    CurrencyModel(2, "United States Dollar", "USD", "\u0024", "https://static.coinstats.app/coins/1650455588819.png"),
    CurrencyModel(3, "United States Dollar", "USD", "\u0024", ""),
    CurrencyModel(4, "United States Dollar", "USD", "\u0024", ""),
)

const val LANGUAGE_ID = "langId"
const val CURRENCY_ID = "currencyId"