package com.hadadas.pokemons.ui.main.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.abstraction.ABVIewHolder
import com.hadadas.pokemons.abstraction.IPokemon

abstract class BaseViewHolder(itemView: View) :  ABVIewHolder(itemView) {
    abstract fun bind(pokemon: IPokemon?, position: Int)
}