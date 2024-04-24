package com.fjr619.currencykmmcompose.data.remote

import com.fjr619.currencykmmcompose.data.remote.model.FailedResponse
import com.fjr619.currencykmmcompose.data.remote.model.RequestException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(httpClientEngine: HttpClientEngine) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
        )
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15000
    }

    HttpResponseValidator {

        //ketika ada error request non 200 akan di handle disini
        handleResponseExceptionWithRequest { exception, _ ->
            when(exception) {
                is ResponseException -> {
                    val responseException = exception.response.body<FailedResponse>()
                    throw RequestException(
                        message = responseException.message
                    )
                }
            }
        }
    }

    defaultRequest {
        url {
            host = "api.currencyapi.com"
            encodedPath = "/v3/latest/"
            protocol = URLProtocol.HTTPS
            contentType(ContentType.Application.Json)
        }

        headers {
            append("apikey", "cur_live_HV09ZZMGxAehfcMLxnRYPTgllLGnWEVF4Mw3x0yr")
        }
    }
}