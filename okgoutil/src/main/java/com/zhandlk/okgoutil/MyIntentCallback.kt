package com.zhandlk.okgoutil

import android.util.Log
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.zhandlk.okgoutil.Interf.MyViewCallback
import java.lang.reflect.ParameterizedType


/**
 * Created by ke_li on 2017/12/19.
 */
class MyIntentCallback<T>(private val requestBean: OkTools.RequestBean, private val myViewCallback: MyViewCallback<T>) : StringCallback() {

    private val NET_ERR = "网络不给力，请检查网络链接"
    override fun onFinish() {
        OkGo.getInstance().cancelTag(requestBean.tag)
    }

    override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
        //TODO 可能重组头部用来设置touken

    }

    override fun onSuccess(response: Response<String>?) {
        if (response!!.code() == 200) {
            if (response.body() != null) {
                try {
                    val interfacesTypes = myViewCallback.javaClass.genericInterfaces[0]
                    val resultType = (interfacesTypes as ParameterizedType).actualTypeArguments
                    val result = GsonFactory.formart<T>(response.body(), resultType[0])
                    myViewCallback.onSuccess(result, requestBean.tag)
                } catch (e: Exception) {
                    Log.e("typeEER", e.message)
                    myViewCallback.onErr("数据服务异常，请联系管理员")
                    return
                }
            } else {
                myViewCallback.onErr("数据服务异常，请联系管理员")
            }
        } else {
            myViewCallback.onErr(response.message()?:"")
        }
    }

    override fun onError(response: Response<String>?) {
        myViewCallback.onErr("数据服务异常，请联系管理员")
    }

}