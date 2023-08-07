package com.hadadas.pokemons.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide
            .with(view.context)
            .load(it)
            .into(view)
    }
}
