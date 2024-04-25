package com.fjr619.currencykmmcompose.data.local.database

import com.fjr619.currencykmmcompose.data.local.database.model.CurrencyEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val realm: Realm
): LocalDataSource {
    override suspend fun insertCurrencyData(currency: CurrencyEntity) {
       realm.write {
           copyToRealm(currency)
       }
    }

    override suspend fun insertCurrencyDatas(currenyList: List<CurrencyEntity>) {
        realm.write {
            currenyList.map {
                copyToRealm(it)
            }
        }
    }

    override fun readCurrencyData(): Flow<List<CurrencyEntity>> {
        return realm.query<CurrencyEntity>()
            .asFlow()
            .map { result ->
                result.list
            }
    }

    override suspend fun cleanUp() {
        return realm.write {
            val currencyCollection = this.query<CurrencyEntity>()
            delete(currencyCollection)
        }
    }
}