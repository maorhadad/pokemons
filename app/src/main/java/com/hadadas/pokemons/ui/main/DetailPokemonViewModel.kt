package com.hadadas.pokemons.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadadas.pokemons.abstraction.IRepositoryAccess
import com.hadadas.pokemons.domain.Pokemon
import kotlinx.coroutines.launch
import java.io.IOException

class DetailPokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val pokemonRepository = (application as IRepositoryAccess).getRepository().pokemonRepository

    private val pokemonLd = MutableLiveData<Pokemon>()
    fun getPokemonLd() = pokemonLd

    fun getPokemonDetails(pokemonName: String) {
        viewModelScope.launch {
            try {
                pokemonRepository.fetchPokemonsDetails(pokemonName, pokemonLd)
            } catch (networkError: IOException) {
                Toast
                    .makeText(getApplication(), "Network error", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}