package com.hadadas.pokemons.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailPokemonViewModel : ViewModel() {

    private val pokemonNameLd = MutableLiveData<String?>()
    fun getPokemonNameLd() = pokemonNameLd
}