package com.hadadas.pokemons.ui.main

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener
import com.hadadas.pokemons.database.getDatabase
import com.hadadas.pokemons.databinding.PokemonShortBinding
import com.hadadas.pokemons.network.PokemonService
import com.hadadas.pokemons.repositories.PokemonRepository
import com.hadadas.pokemons.ui.main.recycler.BaseViewHolder
import com.hadadas.pokemons.ui.main.recycler.PokemonShortViewHolder
import com.hadadas.pokemons.ui.main.recycler.ViewHolderFactory
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var limit = 100
    private var offset = 0
    private val delta = 100

    private val pokemonRepository = PokemonRepository(getDatabase(application), PokemonService())
    val pokemons = pokemonRepository.shortPokemons

    private var _eventNetworkError = MutableLiveData<Boolean>(false)


    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)


    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                pokemonRepository.fetchPokemons(limit, offset)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                offset += delta
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if (pokemons.value.isNullOrEmpty()) _eventNetworkError.value = true
            }
        }
    }

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

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}