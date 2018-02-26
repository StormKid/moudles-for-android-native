package com.part.map

import com.baidu.mapapi.search.sug.SuggestionResult

/**
 * Created by ke_li on 2017/12/21.
 */

/**
 * 地图定位回调
 */
interface MyMapCallback {
    fun onResult(myAddress: MyAddress)

    fun onErr(error:String)
}

interface MyMapSearchCallback{
    fun  onResult(resultList:List<SuggestionResult.SuggestionInfo>)

    fun onErr(error: String)
}