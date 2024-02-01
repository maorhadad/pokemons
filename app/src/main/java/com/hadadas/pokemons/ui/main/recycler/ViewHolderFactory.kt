package com.hadadas.pokemons.ui.main.recycler

import android.view.ViewGroup
import com.hadadas.pokemons.abstraction.ABVIewHolder
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IItemClickListener

abstract class ViewHolderFactory<T : ABVIewHolder> {
    abstract fun createViewHolder(parent: ViewGroup?,
                                  clickListener: IItemClickListener?,
                                  viewType: Int): T
}