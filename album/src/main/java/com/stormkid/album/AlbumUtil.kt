package com.stormkid.album

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget

class AlbumUtil private constructor(){

    data class MyColor( @ColorRes val mainColor: Int,  @ColorRes val selectColor: Int, @ColorRes val unSelectColor: Int)

    private lateinit var context: Context
    private lateinit var widget:Widget
    companion object {
        fun initAlbum(c: Context): AlbumUtil {
            Album.initialize(AlbumConfig.newBuilder(c).setAlbumLoader(MyAlbumLoader()).build())
            return AlbumUtil().apply {
                this.context = c
            }
        }
    }


    fun getAlbum(lisener:(ArrayList<AlbumFile>)->Unit) {
        Album.video(context).singleChoice().onResult { lisener.invoke(it) }
                .camera(false)
                .widget(widget)
                .start()
    }


    fun startVideo(lisener:(String)->Unit,onCancel:()->Unit) {
        Album.camera(context).video().onResult { lisener.invoke(it) }.onCancel { onCancel.invoke() }.
                limitDuration(60000L).quality(1).start()
    }

    fun startCamera() {
        Album.camera(context).image().start()
    }


    @SuppressLint("ResourceType")
    fun useSelfWidget(colors: MyColor, title: String):AlbumUtil {
        val main = ContextCompat.getColor(context, colors.mainColor)
        val select = ContextCompat.getColor(context, colors.selectColor)
        val unselect = ContextCompat.getColor(context, colors.unSelectColor)

        widget =  Widget.newDarkBuilder(context)
                .bucketItemCheckSelector(unselect, select)
                .mediaItemCheckSelector(unselect, select)
                .navigationBarColor(main)
                .statusBarColor(main)
                .title(title)
                .toolBarColor(main)
                .build()
        return this
    }

}