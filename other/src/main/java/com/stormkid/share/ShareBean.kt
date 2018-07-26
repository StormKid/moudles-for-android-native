package com.stormkid.share

import android.support.annotation.DrawableRes
import com.umeng.socialize.media.UMImage
import java.io.Serializable

/**

@author ke_li
@date 2018/7/25
 */
interface ShareBean : Serializable {
    val url: String
    val title: String
    val content: String
    val unUseImage: Int
}
