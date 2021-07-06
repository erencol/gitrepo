package com.erencol.igitrepo.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erencol.igitrepo.R
import com.google.android.material.progressindicator.CircularProgressIndicator


fun ImageView.loadImage(imageUrl: String){
    Glide.with(this.context).load(imageUrl).into(this)
}