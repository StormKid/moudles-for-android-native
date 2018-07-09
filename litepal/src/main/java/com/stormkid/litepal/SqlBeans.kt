package com.stormkid.litepal

import com.stormkid.litepal.pinyin.CN
import com.stormkid.selecttagview.util.SupportEntity
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
data class City(@Column(unique = true) override var id: String="",
                override var isChoose: Boolean=false,
                override var name: String = "", var code: String = "")
    : LitePalSupport(), SupportEntity, CN {
    override fun chinese(): String = name
}