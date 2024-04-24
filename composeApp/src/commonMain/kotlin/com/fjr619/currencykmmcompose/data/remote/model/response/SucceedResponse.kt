package com.fjr619.currencykmmcompose.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val meta: MetaDataDto,
    val data: Map<String, CurrencyDto>
)

@Serializable
data class MetaDataDto(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String
)

@Serializable
data class CurrencyDto (
    val code: String,
    var value: Double
)

