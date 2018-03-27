package com.moudle.basetool.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager





/**
 * Created by ke_li on 2018/3/26.
 */
object ScreenUtil{
    /**
     * 获取是否存在底部虚拟按键
     * @param context
     * @return
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) hasNavigationBar = rs.getBoolean(id)

        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            when(navBarOverride){
                "1"-> hasNavigationBar = false
                "0"->hasNavigationBar =true
            }
        } catch (e: Exception) {

        }

        return hasNavigationBar
    }

    /**
     * 获取虚拟功能键高度
     * @param context
     * @return
     */
    fun getVirtualBarHeigh(context: Context): Int {
        var vh = 0
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val dm = DisplayMetrics()
        try {
            val c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            vh = dm.heightPixels - context.resources.displayMetrics.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return vh
    }
}