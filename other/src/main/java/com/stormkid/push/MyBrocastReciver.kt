package com.stormkid.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface

/**

@author ke_li
@date 2018/7/25
 */
class MyBrocastReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent==null) return
        try {
            val bundle = intent.extras
            val result = bundle!!.getString(JPushInterface.EXTRA_EXTRA)
            val extra = if (TextUtils.isEmpty(result)) "1" else result
            if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                //                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) { //点击状态栏操作

            }
        } catch (e: Exception) {

        }

    }

}