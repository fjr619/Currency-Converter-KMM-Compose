package com.fjr619.currencykmmcompose.data.remote

import com.fjr619.currencykmmcompose.data.remote.model.response.ApiResponse

interface RemoteDataSource {
    suspend fun getLatest(): ApiResponse
}