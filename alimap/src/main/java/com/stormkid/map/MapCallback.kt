package com.stormkid.map

/**

@author ke_li
@date 2018/6/6
 */
interface MapCallback {
    fun getCity(city: MapProperties)

    fun error()
}