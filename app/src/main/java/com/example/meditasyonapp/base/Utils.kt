package com.example.meditasyonapp.base

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.meditasyonapp.R


const val BASE_URL = "https://media.meditopia.com/"
const val MEDIA_URL = "https://d2r0ihkco3hemf.cloudfront.net/bgxupraW2spUpr_y2.mp3"

fun loadImage(view: View, imageView: ImageView, path: String?) {
    path?.let {
        val glideUrl = GlideUrl(
            path, LazyHeaders.Builder()
                .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
                )
                .build()
        )
        Glide.with(view)
            .load(glideUrl)
            .override(600, 600)
            .error(R.drawable.placeholder)
            .into(imageView)
    }
}