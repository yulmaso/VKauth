package com.yulmaso.vkauth.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("profileImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.getContext())
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(view)
}