package com.stormkid.album

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader

class MyAlbumLoader : AlbumLoader {
    override fun load(p0: ImageView?, p1: AlbumFile?) {
        load(p0,p1?.path?:"")
    }

    override fun load(p0: ImageView?, p1: String?) {
        if (null!=p0)
        Glide.with(p0.context).load(p1).into(p0)
    }

}