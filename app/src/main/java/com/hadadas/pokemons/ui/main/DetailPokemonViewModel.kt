package com.hadadas.pokemons.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadadas.pokemons.database.getDatabase
import com.hadadas.pokemons.domain.Pokemon
import com.hadadas.pokemons.network.PokemonService
import com.hadadas.pokemons.repositories.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DetailPokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val pokemonRepository = PokemonRepository(getDatabase(application), PokemonService())

    private val pokemonLd = MutableLiveData<Pokemon>()
    fun getPokemonLd() = pokemonLd

    fun getPokemonDetails(pokemonName: String) {
        viewModelScope.launch {
            try {
                pokemonRepository.fetchPokemonsDetails(pokemonName, pokemonLd)
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
            }
        }

    }
}