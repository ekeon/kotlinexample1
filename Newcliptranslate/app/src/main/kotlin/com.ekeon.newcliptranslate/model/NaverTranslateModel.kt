package com.ekeon.newcliptranslate.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Bridge on 2017-07-07.
 */
@RealmClass
open class NaverTranslateModel: RealmObject() {
    @SerializedName("message")
    @Expose
    open var message: MessageModel? = null
}

@RealmClass
open class MessageModel: RealmObject() {
    @SerializedName("@type")
    @Expose
    open var type: String? = null

    @SerializedName("@service")
    @Expose
    open var service: String? = null

    @SerializedName("@version")
    @Expose
    open var version: String? = null

    @SerializedName("result")
    @Expose
    open var result: ResultModel? = null
}

@RealmClass
open class ResultModel: RealmObject() {
    @SerializedName("translatedText")
    @Expose
    open var translatedText: String? = null
}

