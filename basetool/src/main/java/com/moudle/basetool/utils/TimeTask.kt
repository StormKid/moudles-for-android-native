package com.moudle.basetool.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer

class TimeTask (finalTime:Long, private val onTick:(Long)->Unit, private val onFinish:()->Unit) : CountDownTimer(finalTime+1050,1000) {


    override fun onFinish() {
        cancel()
        onFinish.invoke()
    }

    @SuppressLint("SetTextI18n")
    override fun onTick(millisUntilFinished: Long) {
        val test = Math.abs(millisUntilFinished / 1000) - 1
        onTick.invoke(test)
    }

}
