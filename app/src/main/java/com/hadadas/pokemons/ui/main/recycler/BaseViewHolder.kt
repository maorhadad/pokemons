package com.hadadas.pokemons.ui.main.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(pokemon: IPokemon?, position: Int)
}