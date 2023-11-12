package dev.passerby.data

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun roundDouble(double: Double): Double {
    val locale = Locale("en", "UK")
    val pattern = if (double >= 10) {
        DECIMAL_FORMAT_PATTERN_TWO
    } else {
        DECIMAL_FORMAT_PATTERN_FOUR
    }
    val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
    decimalFormat.applyPattern(pattern)

    return decimalFormat.format(double).toDouble()
}

private const val DECIMAL_FORMAT_PATTERN_FOUR = "###.####"
private const val DECIMAL_FORMAT_PATTERN_TWO = "###.##"
