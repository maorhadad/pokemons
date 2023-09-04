package com.hadadas.pokemons.games.memorygame.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.games.memorygame.ICard
import com.hadadas.pokemons.ui.main.recycler.ViewHolderFactory

class PokemonsCardAdapterK<T : ICard>(private val vhFactory: ViewHolderFactory<CardBaseViewHolder>,
                                      private val itemClickListener: IItemClickListener) :
        ListAdapter<T, CardBaseViewHolder>(CardsListDiffCallback<T>()) {


    override fun onCreateViewHolder(parent: ViewGroup, @IPokemon.PokemonViewTypes viewType: Int): CardBaseViewHolder {
        return vhFactory.createViewHolder(parent, itemClickListener, viewType)
    }

    override fun onBindViewHolder(holder: CardBaseViewHolder, position: Int) {
        val mCurrent = getItem(position)
        holder.bind(mCurrent, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getCardType() ?: ICard.MEMORY_CARD_DOWN
    }
}