package com.moudle.basetool.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * Created by ke_li on 2018/2/27.
 */
abstract class SelfMainPop(val context: Context) : PopupWindow() {
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        contentView = inflater.inflate(initLayout(), null)
        width = context.resources.displayMetrics.widthPixels
        height = WindowManager.LayoutParams.MATCH_PARENT
        isFocusable = true
        isOutsideTouchable = true
        update()
        val dw = ColorDrawable(0)
        setBackgroundDrawable(dw)
        initPop()
    }

    @LayoutRes
    abstract fun initLayout(): Int

    abstract fun initPop()

    fun showPopupWindow(parent: View) {
        if (!isShowing) {
            if (Build.VERSION.SDK_INT >= 24) {
                // 适配 android 7.0
                val location = IntArray(2)
                parent.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1]
                this.showAtLocation(parent, Gravity.NO_GRAVITY, 0, y + height + 5)
            } else {
                this.showAsDropDown(parent)
            }
        } else {
            this.dismiss()
        }

    }
}