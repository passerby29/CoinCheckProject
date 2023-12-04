package dev.passerby.domain.models

data class LanguageModel(
    val id: Int,
    val languageName: String,
    val nativeName: String,
    var isChecked: Boolean = false
)