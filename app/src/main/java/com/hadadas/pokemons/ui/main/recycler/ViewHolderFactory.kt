package com.hadadas.pokemons.ui.main.recycler

import android.view.ViewGroup
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener

abstract class ViewHolderFactory<T : BaseViewHolder> {
    abstract fun createViewHolder(parent: ViewGroup?,
                                  clickListener: IPokemonClickListener?,
                                  @IPokemon.PokemonViewTypes viewType: Int): T
}