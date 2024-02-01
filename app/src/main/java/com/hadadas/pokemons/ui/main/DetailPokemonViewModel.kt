package com.hadadas.pokemons.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadadas.pokemons.abstraction.INameReader
import com.hadadas.pokemons.abstraction.IRepositoryAccess
import com.hadadas.pokemons.domain.Pokemon
import kotlinx.coroutines.launch
import java.io.IOException

class DetailPokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val pokemonRepository = (application as IRepositoryAccess).getRepository().pokemonRepository

    private val pokemonLd = MutableLiveData<Pokemon>()
    fun getPokemonLd() = pokemonLd

    private var nameReader: INameReader? = null

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

    fun onDestroyView() {
        nameReader?.onDestroy()
    }

    fun initNameReader(nameReader: INameReader) {
        this.nameReader = nameReader
    }

    fun onPokemonClicked() {
        buildTextToRead()?.let { nameReader?.readTextToUser(getApplication(), it) }
    }

    private fun buildTextToRead(): String? {
        val pokemon = pokemonLd.value
        if (pokemon != null) {
            val textToRead = StringBuilder()
            textToRead.append("Pokemon name is ${pokemon.name}. ")
            textToRead.append("its height is ${pokemon.height} decimetres. ")
            textToRead.append("and its weight is ${pokemon.weight} hectograms. ")
            textToRead.append(".")
            return textToRead.toString()
        }
        return null
    }
}