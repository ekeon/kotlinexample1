package com.ekeon.newcliptranslate.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bridge on 2017-07-14.
 */

    val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            return f?.declaringClass == RealmObject::class.java
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }
    }).create()

    val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://openapi.naver.com/v1/language/")
            .client(createOkhttps())
            .build()

    fun createOkhttps(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(interceptor)

        return builder.build()
    }