package com.fjr619.currencykmmcompose.data.local.database.model

import com.fjr619.currencykmmcompose.domain.model.Currency
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

@Serializable
open class CurrencyEntity: RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var code: String = ""
    var value: Double = 0.0
}

fun CurrencyEntity.toDomain() = Currency (
    code, value
)