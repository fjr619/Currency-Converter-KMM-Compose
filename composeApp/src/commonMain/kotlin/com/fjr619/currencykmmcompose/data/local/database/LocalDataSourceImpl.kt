package com.fjr619.currencykmmcompose.data.local.database

import com.fjr619.currencykmmcompose.data.local.database.model.Currency
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val realm: Realm
): LocalDataSource {
    override suspend fun insertCurrencyData(currency: Currency) {
       realm.write {
           copyToRealm(currency)
       }
    }

    override fun readCurrencyData(): Flow<List<Currency>> {
        return realm.query<Currency>()
            .asFlow()
            .map { result ->
                result.list
            }
    }

    override suspend fun cleanUp() {
        return realm.write {
            val currencyCollection = this.query<Currency>()
            delete(currencyCollection)
        }
    }
}