package com.part.pay.alipay

/**
 * Created by ke_li on 2017/12/25.
 */
interface PayResult {
    /**
     * 支付成功
     */
    fun success()

    /**
     * 支付失败
     */
    fun error()

    /**
     * 处理支付后失败的结果
     */
    fun dealMassege(msg:String)

    /**
     * 服务器进行支付中的使用
     */
    fun loading()
}