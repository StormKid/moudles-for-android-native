package com.stormkid.litepal

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
存入数据库的bean
@author ke_li
@date 2018/6/20
 */

/**
 * 城市
 */
data class City(@Column(unique = true)  var id: String="",
                 var isChoose: Boolean=false,
                 var name: String = "", var code: String = "")
    : LitePalSupport()