package com.stormkid

import android.app.Application
import cn.jpush.android.api.JPushInterface

/**

@author ke_li
@date 2018/7/25
 */
object JpushUtil {
    fun initJpush(application: Application){
        JPushInterface.setDebugMode(true) //TODO 正式上线版本需要修改此处
        JPushInterface.init(application)
        JPushInterface.setLatestNotificationNumber(application, 10)
    }
}