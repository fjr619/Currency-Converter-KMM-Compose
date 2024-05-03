package com.fjr619.currencykmmcompose.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
actual class DecimalFormat {
    actual fun format(double: Double): String {
        val formatter = NSNumberFormatter()
        formatter.minimumFractionDigits = 0u
        formatter.maximumFractionDigits = 2u
        formatter.numberStyle = 1u //Decimal
        return formatter.stringFromNumber(NSNumber(double))!!
    }
}