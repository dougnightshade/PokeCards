package com.manickchand.pokecards.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadGlideImage(
    context: Context,
    imageUrl: String
) {
    Glide.with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun AppCompatActivity.showToast(msg:String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()