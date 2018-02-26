package com.part.map

import com.baidu.location.Address
import com.baidu.location.BDLocation

/**
 * Created by ke_li on 2017/12/21.
 */

/**
 * address : 定位后所有的地址显示类
 * direction: 定位后组装String输出的类
 */
data class MyAddress constructor(val location: BDLocation,val address: Address,val direction:String)
