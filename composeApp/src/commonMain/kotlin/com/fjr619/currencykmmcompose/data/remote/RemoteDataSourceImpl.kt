package com.fjr619.currencykmmcompose.data.remote

import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.remote.model.request.RequestLatest
import com.fjr619.currencykmmcompose.data.remote.model.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class RemoteDataSourceImpl (
    private val httpClient: HttpClient,
): RemoteDataSource {
    override suspend fun getLatest(): ApiResponse {
       return httpClient.get(RequestLatest()).body()
    }
}