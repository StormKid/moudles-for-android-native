package com.stormkid.map

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption

/**

@author ke_li
@date 2018/6/6
 */
object MapLocation {
    fun initClient(context: Context, mapCallback: MapCallback) = AMapLocationClient(context.applicationContext).apply {
        val properties = MapProperties()
        this.setLocationListener {
            this.stopLocation()
            if (it == null) mapCallback.error()
            else {
                properties.cityCode = it.cityCode
                properties.cityName = it.city
                mapCallback.getCity(properties)
            }
        }
        val option = AMapLocationClientOption()
        option.isOnceLocation = true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        option.httpTimeOut = 20000L
        option.isNeedAddress = true
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.Transport
        this.setLocationOption(option)
    }

}