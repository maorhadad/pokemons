package com.hadadas.pokemons.ui.main.recycler

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IItemClickListener

class PokemonsAdapterK<T : IPokemon>(private val vhFactory: ViewHolderFactory<BaseViewHolder>,
                                     private val itemClickListener: IItemClickListener) :
        PagingDataAdapter<T, BaseViewHolder>(PokemonListDiffCallback<T>()) {


    override fun onCreateViewHolder(parent: ViewGroup, @IPokemon.PokemonViewTypes viewType: Int): BaseViewHolder {
        return vhFactory.createViewHolder(parent, itemClickListener, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val mCurrent = getItem(position)
        holder.bind(mCurrent, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: IPokemon.POKEMON_SHORT
    }
}