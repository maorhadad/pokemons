package com.hadadas.pokemons.ui.main.recycler

import androidx.recyclerview.widget.DiffUtil
import com.hadadas.pokemons.abstraction.IPokemon

class PokemonListDiffCallback<T : IPokemon> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getPokemonId() == newItem.getPokemonId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getPokemonId() == newItem.getPokemonId() && oldItem.getPokemonName() == newItem.getPokemonName()
    }


}