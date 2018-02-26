package com.part.map

import android.content.Context
import android.text.TextUtils
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult

/**
 * Created by ke_li on 2017/12/21.
 */
class MyLocationResult constructor(myMapCallback: MyMapCallback?, context: Context) : BDAbstractLocationListener() {

    private val callback = myMapCallback
    private val context = context

    override fun onReceiveLocation(p0: BDLocation?) {
        if (p0 != null && (p0.locType == 61 || p0.locType == 161)) {
            val address = p0.address
            var directory  = if (!TextUtils.isEmpty(p0.addrStr)) BaiduUtils.getDirection(address) else p0.addrStr
            val result = MyAddress(p0, address, directory)
            callback?.onResult(result)
        }
    }

    override fun onLocDiagnosticMessage(p0: Int, p1: Int, p2: String?) {
        super.onLocDiagnosticMessage(p0, p1, p2)
        when (p0) {
            62 -> callback?.onErr(context.getString(R.string.err_62))
            63 -> callback?.onErr(context.getString(R.string.err_63))
            167 -> callback?.onErr(context.getString(R.string.err_167))
        }
    }

}


class MySearchResult constructor(myMapSearchCallback: MyMapSearchCallback?) : OnGetSuggestionResultListener {
    private val callback = myMapSearchCallback
    override fun onGetSuggestionResult(p0: SuggestionResult?) {
        if (p0 != null && p0.error == SearchResult.ERRORNO.NO_ERROR) {
            val allSuggestions = p0.allSuggestions
            callback?.onResult(allSuggestions)
        } else {
            callback?.onErr("地点未获取或网络有问题，请检查后重新搜索")
        }
    }

}

