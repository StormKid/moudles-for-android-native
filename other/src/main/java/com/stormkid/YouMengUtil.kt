package com.stormkid

import android.app.Application
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig

/**

@author ke_li
@date 2018/7/25
 */
object YouMengUtil {
     private   const  val umengKey = "5b441969a40fa36fd6000038"
     private const val wxID = "wxb9814f26867bf795"
     private const val wxScrectId = "be8c1f7a030111c1eb4e081e853529ed"
    private const val QQID = "1107003238"
    private const val QQKEY = "AnoGM9B05doUgUw2"
    /**
     *  init友盟
     */
    fun initDebug(application: Application){
        UMConfigure.init(application, umengKey,"Umeng",UMConfigure.DEVICE_TYPE_PHONE,"")
        UMConfigure.setLogEnabled(true)
        UMConfigure.setEncryptEnabled(false)
    }

    fun init(application: Application){
        UMConfigure.init(application, umengKey,"Umeng",UMConfigure.DEVICE_TYPE_PHONE,"")
        UMConfigure.setLogEnabled(false)
        UMConfigure.setEncryptEnabled(true)
    }


    fun initShare(){
        PlatformConfig.setWeixin(wxID, wxScrectId)
        PlatformConfig.setQQZone(QQID, QQKEY)
    }



}