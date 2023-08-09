package com.hadadas.pokemons.ui.main

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener
import com.hadadas.pokemons.abstraction.IRepositoryAccess
import com.hadadas.pokemons.databinding.PokemonShortBinding
import com.hadadas.pokemons.ui.main.recycler.BaseViewHolder
import com.hadadas.pokemons.ui.main.recycler.PokemonShortViewHolder
import com.hadadas.pokemons.ui.main.recycler.ViewHolderFactory

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val pokemonRepository = (application as IRepositoryAccess).getRepository().pokemonRepository
    val pokemons = pokemonRepository
        .getAllPokemons()
        .cachedIn(viewModelScope)

    fun getViewHolderFactory(): ViewHolderFactory<BaseViewHolder> {
        return object : ViewHolderFactory<BaseViewHolder>() {

            override fun createViewHolder(parent: ViewGroup?,
                                          clickListener: IPokemonClickListener?,
                                          @IPokemon.PokemonViewTypes viewType: Int): BaseViewHolder {
                when (viewType) {
                    IPokemon.POKEMON -> {
                        TODO()
                    }
                    else -> {
                        val psb: PokemonShortBinding =
                            PokemonShortBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                        return PokemonShortViewHolder(psb, clickListener)
                    }
                }
            }
        }
    }
}