package com.hadadas.pokemons.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hadadas.pokemons.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        GlideApp
            .with(view.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.pokaball2)
            .into(view)
    }
}
