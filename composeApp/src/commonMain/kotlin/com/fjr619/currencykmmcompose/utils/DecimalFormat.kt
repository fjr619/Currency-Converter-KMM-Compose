package com.fjr619.currencykmmcompose.utils

expect class DecimalFormat() {
    fun format(double: Double): String
}

fun String.formatDecimalSeparator(): String {
    val split = this.split(".")
    var result = ""
    if (split.isEmpty() || split.size < 2) {
        result = this.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
    } else {
        result = split.first().reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
        result = result+"."+split.last()
    }
    return result

//        return this
//            .reversed()
//            .chunked(3)
//            .joinToString(",")
//            .reversed()
}