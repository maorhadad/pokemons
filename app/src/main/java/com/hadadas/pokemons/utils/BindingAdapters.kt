package com.hadadas.pokemons.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hadadas.pokemons.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        if (imageUrl.isNullOrEmpty()) {
            return
        }
        GlideApp
            .with(view.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.pokaball_small2)
            .into(view)
    }
}

@BindingAdapter("pokemonName")
fun pokemonName(view: TextView, name: String?) {
    if (name != null) {
        view.text = name
    } else {
        view.text = ""
    }
}


