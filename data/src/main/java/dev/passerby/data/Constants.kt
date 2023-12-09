package dev.passerby.data

import android.content.Context
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel


class Constants(context: Context, languageId: Int = 0) {

    val languagesList = arrayListOf(
        LanguageModel(
            0,
            context.resources.getStringArray(R.array.english_title)[languageId],
            "English"
        ),
        LanguageModel(
            1,
            context.resources.getStringArray(R.array.russian_title)[languageId],
            "Русский"
        ),
        LanguageModel(
            2,
            context.resources.getStringArray(R.array.ukrainian_title)[languageId],
            "Українська"
        ),
        LanguageModel(
            3,
            context.resources.getStringArray(R.array.spanish_title)[languageId],
            "Español"
        ),
        LanguageModel(
            4,
            context.resources.getStringArray(R.array.kazakh_title)[languageId],
            "Қазақ"
        ),
    )

    val currenciesList = arrayListOf(
        CurrencyModel(
            0,
            context.resources.getStringArray(R.array.us_dollar_title)[languageId],
            "USD",
            "\u0024",
            R.drawable.ic_usa
        ),
        CurrencyModel(
            1,
            context.resources.getStringArray(R.array.ruble_title)[languageId],
            "RUB",
            "\u20bd",
            R.drawable.ic_rub
        ),
        CurrencyModel(
            2,
            context.resources.getStringArray(R.array.hryvnia_title)[languageId],
            "UAH",
            "\u20b4",
            R.drawable.ic_uah
        ),
        CurrencyModel(
            3,
            context.resources.getStringArray(R.array.euro_title)[languageId],
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