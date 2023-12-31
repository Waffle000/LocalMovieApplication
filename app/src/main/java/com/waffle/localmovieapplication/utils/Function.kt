package com.waffle.localmovieapplication.utils

import android.app.Activity
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.waffle.localmovieapplication.R

fun ImageView.loadImage(url: String?, placeholder: Int = R.drawable.ic_launcher_foreground) {
    Glide.with(this)
        .load("https://image.tmdb.org/t/p/original/$url")
        .apply(
            RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(placeholder)
                .error(placeholder)
        )
        .into(this)
}