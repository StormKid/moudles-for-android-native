package org.jsly.aliyunplayer.album

import android.content.Context
import android.support.annotation.ColorInt
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import org.jsly.aliyunplayer.album.ReCallCode.ALBUM_CODE
import org.jsly.aliyunplayer.album.ReCallCode.CAMERA_CODE
import org.jsly.aliyunplayer.album.ReCallCode.WITHE_TEXT_TYPE

/**
 * Created by ke_li on 2018/1/24.
 * 获取相册和拍照相关
 */
class FileManager constructor(val context: Context) {


    data class AlbumeFile constructor(val path: String, val name: String)

    data class WidgetProfiles constructor(val title: String = "我的相册", @ColorInt val stateBarColorInt: Int = 0
                                          , @ColorInt val toolbarColorInt: Int = stateBarColorInt, @ColorInt val navigationColorInt: Int = stateBarColorInt,
                                          @ColorInt val chooseColor: Int = stateBarColorInt, @ColorInt val unChooseColor: Int = stateBarColorInt
    )

    fun getWidget(widgetProfiles: WidgetProfiles,type:Int) = Builder(
            let {
                val build = if (type== WITHE_TEXT_TYPE) Widget.newDarkBuilder(context)
                else Widget.newLightBuilder(context)
                build.title(widgetProfiles.title)
                        .toolBarColor(widgetProfiles.toolbarColorInt)
                        .statusBarColor(widgetProfiles.stateBarColorInt)
                        .navigationBarColor(widgetProfiles.navigationColorInt)
                        .bucketItemCheckSelector(widgetProfiles.unChooseColor, widgetProfiles.chooseColor)
                        .mediaItemCheckSelector(widgetProfiles.unChooseColor, widgetProfiles.chooseColor)
                        .build()
            }
    )

    fun startCamera(listener: (String) -> Unit) {
        Album.camera(context).image()
                .requestCode(CAMERA_CODE).onResult { _, result -> listener.invoke(result) }.start()
    }

    inner class Builder constructor(val widget: Widget) {

        fun chooseImgs(listener: (ArrayList<AlbumeFile>) -> Unit) {
            val albumList: ArrayList<AlbumeFile> = ArrayList()
            Album.image(context).multipleChoice().requestCode(ALBUM_CODE).widget(widget).selectCount(9)
                    .onResult { requestCode, result ->
                        if (requestCode == ALBUM_CODE) {
                            result.forEach { if (it.isChecked) albumList.add(AlbumeFile(it.path, it.name)) }
                            listener.invoke(albumList)
                        }
                    }
                    .start()
        }
    }


}
