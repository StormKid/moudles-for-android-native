package com.zhandlk.okgoutil.Interf

import com.lzy.okgo.model.Progress
import java.io.File

/**

@author ke_li
@date 2018/7/9
 */
interface MyFileNetCallback {
    fun success(file: File)
    fun progress(process: Progress)
    fun err(msg:String)
}