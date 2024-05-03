package com.fjr619.currencykmmcompose.utils

actual class DecimalFormat {
    actual fun format(double: Double): String {
        val df = java.text.DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = 2
        df.isDecimalSeparatorAlwaysShown = false
        return df.format(double)
    }
}