package com.moudle.basetool.utils

import android.util.Log

/**
 * Created by ke_li on 2017/12/20.
 */
open class LogUtil {
    companion object {

        private val isOpen: Boolean = true
        val tag = "myApp"

        fun e(msg: String) {
            if (isOpen) e(tag, msg)
        }


        fun e(tag: String, msg: String ) {
            if (isOpen) Log.e(tag, msg)
        }

        fun I(tag: String, msg: String) {
            if (isOpen) Log.i(tag, msg)
        }

        fun I(msg: String) {
            if (isOpen) I(tag, msg)
        }

        fun d(msg: String){
            if (isOpen)Log.d(tag, msg)
        }

        fun d(tag: String,msg: String){
            if (isOpen)Log.d(tag,msg)
        }
    }


}