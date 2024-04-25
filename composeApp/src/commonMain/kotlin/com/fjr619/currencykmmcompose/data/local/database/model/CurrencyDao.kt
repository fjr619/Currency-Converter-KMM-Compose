package com.fjr619.currencykmmcompose.data.local.database.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

@Serializable
open class CurrencyDao: RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var code: String = ""
    var value: Double = 0.0
}