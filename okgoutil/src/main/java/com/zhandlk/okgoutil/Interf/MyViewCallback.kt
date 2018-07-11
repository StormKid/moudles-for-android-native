package com.zhandlk.okgoutil.Interf

/**

@author ke_li
@date 2018/7/9
 */
interface MyViewCallback<in T> {
    fun onSuccess(bean:T,tag:String)

    fun onErr(massage:String)
}