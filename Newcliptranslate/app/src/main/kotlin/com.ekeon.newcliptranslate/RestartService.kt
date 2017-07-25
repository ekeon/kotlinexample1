package com.ekeon.newcliptranslate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by Bridge on 2017-07-14.
 */
class RestartService: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action.equals("ACTION.RESTART.PersistentService"))  {
            Log.d("TAG","Restart called")
            val i = Intent(context, PersistentService::class.java)
            context?.startService(i)
        }

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("TAG","BOOT COMPLETED")
            val i = Intent(context, PersistentService::class.java)
            context?.startService(i)
        }
    }
}