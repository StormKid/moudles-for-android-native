package com.stormkid.okhttpkt.core

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.stormkid.okhttpkt.rule.CallbackRule
import com.stormkid.okhttpkt.utils.GsonFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType


/**

@author ke_li
@date 2018/5/25
 */
class OkCallback<T>(private val callbackRule: CallbackRule<T>, private val need: OkCallbackNeed) : Callback {

    private val handler = Handler(Looper.getMainLooper())
    override fun onFailure(call: Call?, e: IOException?) {
        callbackRule.onFailed(need.err_msg)
        call!!.cancel()
    }

    override fun onResponse(call: Call?, response: Response?) {
        if (null != response) {
            if (response.isSuccessful) {
                if (null == response.body()) {
                    handler.post {  callbackRule.onFailed(need.err_msg)}
                    return
                } else {
                    val body = response.body()?.string() ?: ""
                    //获取接口上的泛型注入
                    try {
                        val interfacesTypes = callbackRule.javaClass.genericInterfaces[0]
                        val resultType = (interfacesTypes as ParameterizedType).actualTypeArguments
                        val result = GsonFactory.formart<T>(body, resultType[0])
                        handler.post {   callbackRule.onSuccess(result, need.flag)}
                   }catch (e:Exception){
                        Log.e("typeEER",e.message)
                        handler.post {   callbackRule.onFailed("数据服务异常，请联系管理员")}
                        return
                    }
                }
            } else    handler.post { callbackRule.onFailed(response.message())}
        } else    handler.post { callbackRule.onFailed(need.err_msg)}
        call!!.cancel()
    }


}