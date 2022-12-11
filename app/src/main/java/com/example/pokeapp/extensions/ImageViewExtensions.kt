package com.example.pokeapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokeapp.R

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_bug_report)
        .into(this)

}