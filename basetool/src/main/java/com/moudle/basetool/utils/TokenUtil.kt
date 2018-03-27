package com.moudle.basetool.utils

import android.content.Context
import android.text.TextUtils
import com.moudle.basetool.utils.Constants.token


/**
 * Created by ke_li on 2017/12/18.
 */
object TokenUtil {
        fun getToken(context: Context):String{
            val token = SharePerfrenceUtil[context, token, ""] as String
            return if (TextUtils.isEmpty(token)) ""
            else token
        }

        fun setToken(context: Context,token:String){
            SharePerfrenceUtil.put(context, Constants.token,token)
        }

        fun putUserInfo(context: Context,userName:String,pwd:String,nickName:String){
            SharePerfrenceUtil.put(context,Constants.USER_NAME,userName)
            SharePerfrenceUtil.put(context,Constants.PWD,pwd)
            SharePerfrenceUtil.put(context,Constants.NICK_NAME,nickName)
        }

}