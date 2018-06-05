package com.moudle.basetool.utils

import android.content.Context
import com.moudle.basetool.ui.BaseActivity
import com.moudle.basetool.ui.DialogCallback
import com.moudle.basetool.ui.MyAlertDialog
import com.moudle.basetool.ui.RebackActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Setting
import java.io.File

/**

@author ke_li
@date 2018/6/5
 */
class PermisstionUtil {
    inner class Builder(val context: Context) {
        private val permission = AndPermission.with(context)
        private var permissionGroup = arrayOf<String>()
        fun initGroup(group: Array<String>){
            permissionGroup = group
        }

        fun getRunTimePermission(callback:()->Unit){
          permission.runtime().permission(permissionGroup).onGranted { callback.invoke() }.onDenied{ callUser(it,{getRunTimePermission(callback)}) }.start()
        }

        fun getInstallApkPermission(file:File,callback: (File) -> Unit){
            permission.install().file(file).rationale{context,_,request->
                MyAlertDialog.Builder().setTitle("是否安装非应用商店应用").build(context).excute(object :DialogCallback{
                    override fun ok() {
                        request.execute()
                    }

                    override fun no() {
                        request.cancel()
                    }

                })
            }.onGranted { callback.invoke(it) }.onDenied{ ManagerUtils.returnActivity(context,RebackActivity::class.java) }.start()
        }

        private fun callUser(list: MutableList<String>,function: ()->Unit){
            if (AndPermission.hasAlwaysDeniedPermission(context, list)) {
                // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                MyAlertDialog.Builder().setTitle("应用没有权限无法继续运行，是否去设置中授权").build(context).excute(object : DialogCallback{
                    override fun ok() {
                        AndPermission.with(context)
                                .runtime()
                                .setting()
                                .onComeback{
                                    function()
                                }
                                .start()
                    }

                    override fun no() {
                         ManagerUtils.returnActivity(context,RebackActivity::class.java)
                    }

                })

            }
        }
    }


}