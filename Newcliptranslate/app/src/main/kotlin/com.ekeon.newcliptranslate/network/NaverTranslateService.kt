package com.ekeon.newcliptranslate.network

import com.ekeon.newcliptranslate.model.NaverTranslateModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Bridge on 2017-07-07.
 */
interface NaverTranslateService {
    @FormUrlEncoded
    @POST("translate")
    @Headers("X-Naver-Client-Id: g7vNF49xO1AEPoQs9PxY", "X-Naver-Client-Secret: 6JW5DqB1rP")
    fun getTranslate(@Field("source") source: String, @Field("target") target: String, @Field("text") text: String): Observable<NaverTranslateModel>
}