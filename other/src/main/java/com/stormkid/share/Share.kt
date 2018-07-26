package com.stormkid.share

import android.app.Activity
import android.content.Context
import com.stormkid.R
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

/**

@author ke_li
@date 2018/7/25
 */
object Share{
    fun shareAction(context: Activity,shareBean: ShareBean){
        val action = ShareAction(context).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ)
        val thumb =
        when (shareBean){
            is ShareDatas.ImageNet->  UMImage(context,shareBean.image)
            is ShareDatas.ImageBitmap-> UMImage(context,shareBean.image)
            is ShareDatas.ImageFile-> UMImage(context,shareBean.image)
            is ShareDatas.ImageSource-> UMImage(context,shareBean.image)
            else -> UMImage(context,shareBean.unUseImage)
        }
        val web = UMWeb(shareBean.url).apply {
            this.setThumb(thumb)
            this.title = shareBean.title
            this.description = shareBean.content
        }
        action.withMedia(web).open()
    }

    fun initResult(context: Activity,onActivityResult: ShareDatas.OnActivityResult){
        UMShareAPI.get(context).onActivityResult(onActivityResult.requestCode,onActivityResult.resultCode,onActivityResult.intent)
    }

}
