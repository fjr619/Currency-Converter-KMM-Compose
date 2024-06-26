package com.fjr619.currencykmmcompose.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FailedResponse(
    @SerialName("message") val message: String?
)