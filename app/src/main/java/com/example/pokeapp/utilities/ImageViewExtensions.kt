package com.example.pokeapp.utilities

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokeapp.R

fun ImageView.loadUrl(url: String) {

    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .error(R.drawable.pokemon_empty_state)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}