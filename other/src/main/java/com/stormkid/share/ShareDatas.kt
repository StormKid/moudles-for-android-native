package com.stormkid.share

import android.content.Intent
import android.graphics.Bitmap
import android.support.annotation.DrawableRes
import java.io.File

/**

@author ke_li
@date 2018/7/25
 */
class ShareDatas {
    data class ImageNet(override val url: String, override val title: String, override val content: String, val image:String, override val unUseImage: Int):ShareBean
    data class ImageSource(override val url: String, override val title: String, override val content: String, @DrawableRes val image:Int, override val unUseImage: Int):ShareBean
    data class ImageFile(override val url: String, override val title: String, override val content: String, val image:File, override val unUseImage: Int):ShareBean
    data class ImageBitmap(override val url: String, override val title: String, override val content: String, val image:Bitmap, override val unUseImage: Int):ShareBean
    data class OnActivityResult(val requestCode:Int,val resultCode:Int,val intent: Intent)


}