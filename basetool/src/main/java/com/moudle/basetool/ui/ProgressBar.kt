package com.moudle.basetool.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.moudle.basetool.R
import kotlinx.android.synthetic.main.progressbar.*

/**
 * Created by ke_li on 2018/3/23.
 */
class ProgressBar(context: Context):Dialog (context, R.style.trans_dialog)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progressbar)
    }


    fun setTitle(value:String){
        my_title.text = value
    }

}