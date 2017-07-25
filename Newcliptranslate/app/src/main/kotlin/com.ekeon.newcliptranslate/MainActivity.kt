package com.ekeon.newcliptranslate

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val receiver: BroadcastReceiver = RestartService()
//    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main.setOnClickListener { Log.d("TAG","hi") }

        initService()
    }

    override fun onDestroy() {
        try {
            unregisterReceiver(receiver)
        } catch(e: Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }

    fun initService() {
        val restartService = RestartService()
        val intent = Intent(this, PersistentService::class.java)

        val intentFilter = IntentFilter("com.ekeon.newcliptranslate.restart")
        registerReceiver(restartService, intentFilter)
        startService(intent)
    }
}
