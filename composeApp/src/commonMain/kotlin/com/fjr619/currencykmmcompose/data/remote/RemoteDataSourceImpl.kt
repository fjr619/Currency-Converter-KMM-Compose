package com.fjr619.currencykmmcompose.data.remote

import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource

class RemoteDataSourceImpl (
    private val preferencesDataSource: PreferencesDataSource
): RemoteDataSource {
}