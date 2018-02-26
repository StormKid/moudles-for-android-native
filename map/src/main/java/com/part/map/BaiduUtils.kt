package com.part.map

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.baidu.location.Address
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.MyLocationConfiguration
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption

/**
 * 这里使用百度地图的各种工具需要的封装
 * Created by ke_li on 2017/12/21.
 */
object BaiduUtils {


    private val WIFI_TIME_OUT = 60000

    /**
     * 备用方法，如果str为空启用此方法
     */
    fun getDirection(address: Address?): String {
        var direction = ""
        if (address != null) direction = address.province + address.city + address.district + address.street + address.streetNumber
        return direction
    }


    /**
     * 开始定位
     */
    fun startLocation(context: Context, callback: MyMapCallback): LocationClient
            = LocationClient(context).run {
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.setCoorType("bd09ll")
        option.isOpenGps = true
        option.setWifiCacheTimeOut(WIFI_TIME_OUT)
        option.setIsNeedAddress(true)
        option.setIsNeedLocationDescribe(true)
        this.locOption = option
        this.registerLocationListener(MyLocationResult(callback, context))
        this.start()
        return this
    }


    /**
     * 开始搜索
     */
    fun startSearch(city: String, key: String, listener: MyMapSearchCallback): SuggestionSearch
            = SuggestionSearch.newInstance().run {
        this.setOnGetSuggestionResultListener(MySearchResult(listener))
        this.requestSuggestion(object : SuggestionSearchOption() {
        }.city(city).keyword(key).citylimit(true))
        return this
    }

    /**
     * 开始搜索
     */
    fun startSearch(city: String,key: String,listener: (PoiResult?)->Unit) =PoiSearch.newInstance().let {

        it.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener{
            override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {
            }

            override fun onGetPoiResult(p0: PoiResult?) {

                listener.invoke(p0)
            }

            override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
            }
        })
        it.searchInCity(PoiCitySearchOption().city(city).keyword(key).pageNum(10).isReturnAddr(true))
        it
    }



    /**
     * 更新定位地图显示
     */
    fun initMap(mapView: MapView, location: BDLocation) =
            mapView.map.run {
                val child = mapView.getChildAt(1)
                if (child != null && (child is ImageView || child is ZoomControls)) {
                    child.visibility = View.INVISIBLE
                }
                mapView.showZoomControls(false)
                mapView.showScaleControl(false)
                this.isMyLocationEnabled = true
                val locationInit = MyLocationData.Builder().accuracy(location.radius).latitude(location.latitude).longitude(location.longitude).direction(100f).build()
                val myLocationConfiguration = MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null)
                this.setMyLocationData(locationInit)
                this.setMaxAndMinZoomLevel(18f, 18f)
                this.setMyLocationConfiguration(myLocationConfiguration)
            }
    fun initMap(mapView: MapView, bean:SuggestionResult.SuggestionInfo){
        val location:BDLocation = BDLocation()
        location.latitude = bean.pt.latitude
        location.longitude = bean.pt.longitude
        initMap(mapView,location)
    }
    fun initMap(mapView: MapView, latitude:Double,longitude:Double){
        val location:BDLocation = BDLocation()
        location.latitude = latitude
        location.longitude = longitude
        initMap(mapView,location)
    }
}

