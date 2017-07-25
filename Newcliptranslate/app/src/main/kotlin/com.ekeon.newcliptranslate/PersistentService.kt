package com.ekeon.newcliptranslate

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.ekeon.newcliptranslate.network.NaverTranslateService
import com.ekeon.newcliptranslate.network.retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

/**
 * Created by Bridge on 2017-07-14.
 */
class PersistentService: Service() {

    val REGEX_ENGLISH = "^[a-zA-Z]{1,30}"
    val naverTranslateService: NaverTranslateService = retrofit.create(NaverTranslateService::class.java)

    override fun onCreate() {
        Log.d("TAG", "service on")
        unRegisterRestartAlarm()

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.addPrimaryClipChangedListener {
            val item = clipboardManager.primaryClip.getItemAt(0)
            val cliptext = item.text.toString()

            if (cliptext.matches(REGEX_ENGLISH.toRegex())) getTranslate("" + cliptext)
        }

        super.onCreate()
    }

    override fun onDestroy() {
        registerRestartAlarm()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    fun getTranslate(beforeT: String) {
        naverTranslateService.getTranslate("en", "ko", beforeT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        { translate ->
                            Toast.makeText(this, translate.message?.result?.translatedText, Toast.LENGTH_LONG).show() },
                        { error -> error.printStackTrace() })
    }

    fun registerRestartAlarm() {
        val intent = Intent(this, RestartService::class.java)
        intent.setAction("ACTION.RESTART.PersistentService")
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        var firstTime = SystemClock.elapsedRealtime()
        firstTime += 1000*10

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime * 10, 1000, sender)
    }

    fun unRegisterRestartAlarm() {
        val intent = Intent(this, RestartService::class.java)
        intent.setAction("ACTION.RESTART.PersistentService")
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(sender)
    }
}