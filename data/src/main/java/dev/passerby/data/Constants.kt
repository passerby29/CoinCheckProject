package dev.passerby.data

import android.content.Context
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel


class Constants(context: Context) {
    val languagesList = arrayListOf(
        LanguageModel(0, context.getString(R.string.english_title), "English"),
        LanguageModel(1, context.getString(R.string.russian_title), "Русский"),
        LanguageModel(2, context.getString(R.string.ukrainian_title), "Українська"),
        LanguageModel(3, context.getString(R.string.spanish_title), "Español"),
        LanguageModel(4, context.getString(R.string.kazakh_title), "Қазақ"),
    )

    val currenciesList = arrayListOf(
        CurrencyModel(
            0,
            "United States Dollar",
            "USD",
            "\u0024",
            R.drawable.ic_usa
        ),
        CurrencyModel(
            1,
            "Russia Ruble",
            "RUB",
            "\u20bd",
            R.drawable.ic_rub
        ),
        CurrencyModel(
            2,
            "Ukraine Hryvnia",
            "UAH",
            "\u20b4",
            R.drawable.ic_uah
        ),
        CurrencyModel(
            3,
            "Euro",
            "EUR",
            "\u20ac",
            R.drawable.ic_euro
        ),
    )

    companion object {
        const val LANGUAGE_ID = "langId"
        const val CURRENCY_ID = "currencyId"
    }
}