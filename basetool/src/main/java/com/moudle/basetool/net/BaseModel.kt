package com.moudle.basetool.net

import java.io.Serializable

/**
 * Created by ke_li on 2017/12/19.
 */
data class BaseModel constructor(val code:Int, val message:String, val success:Boolean, var content:String) : Serializable