package com.hadadas.pokemons.ui.main.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener

class PokemonsAdapterK<T : IPokemon>(private val vhFactory: ViewHolderFactory<BaseViewHolder>,
                                     private val itemClickListener: IPokemonClickListener) :
        RecyclerView.Adapter<BaseViewHolder>() {

    private val fullList: AsyncListDiffer<T> = AsyncListDiffer<T>(this, PokemonListDiffCallback<T>())

    override fun onCreateViewHolder(parent: ViewGroup, @IPokemon.PokemonViewTypes viewType: Int): BaseViewHolder {
        return vhFactory.createViewHolder(parent, itemClickListener, viewType)
    }

    override fun getItemCount(): Int = fullList.currentList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val mCurrent: IPokemon = fullList.currentList[position]
        holder.bind(mCurrent, position)
    }

    override fun getItemViewType(position: Int): Int {
        return fullList.currentList[position].getType()
    }

    fun submitList(deviceInformation: List<T>?) {
        fullList.submitList(deviceInformation)
    }
}