package com.part.pay.alipay

import android.app.Activity
import android.os.AsyncTask
import com.alipay.sdk.app.PayTask

/**
 * Created by ke_li on 2017/12/25.
 */
class PayTask constructor(val context: Activity, val callback: PayResult) : AsyncTask<String, Int, Map<String, String>>() {

     lateinit var pay:PayTask

    override fun onPreExecute() {
        super.onPreExecute()
        pay = PayTask(context)
    }

    override fun doInBackground(vararg params: String?): Map<String, String> {
        val param = params[0]
        return pay.payV2(param, true)
    }

    override fun onPostExecute(result: Map<String, String>?) {
        super.onPostExecute(result)
        if (null == result) return
        val resultCode = result["resultStatus"]
        when (resultCode){
            "9000"-> callback.success()
            "8000"-> callback.loading()
            "6001"-> callback.dealMassege("支付取消")
            "6002"-> callback.dealMassege("网络连接异常，请检查网络连接")
            "4000"-> callback.error() //支付错误
        }
    }

}