package com.ekeon.newcliptranslate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ekeon.newcliptranslate.network.NaverTranslateService
import com.ekeon.newcliptranslate.network.retrofit

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val naverTranslateService: NaverTranslateService = retrofit.create(NaverTranslateService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getTranslate("hello")
    }

    fun getTranslate(beforeT: String) {
        naverTranslateService.getTranslate("en", "ko", beforeT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        { translate -> Log.d("TAG", "There ${translate.message?.result?.translatedText} is") },
                        { error -> error.printStackTrace() })
    }

}
