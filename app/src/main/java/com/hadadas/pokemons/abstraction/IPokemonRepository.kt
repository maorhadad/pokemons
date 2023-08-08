package com.hadadas.pokemons.abstraction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.hadadas.pokemons.domain.Pokemon
import com.hadadas.pokemons.domain.PokemonShort

interface IPokemonRepository {
    suspend fun fetchPokemons(limit: Int, offset: Int): List<PokemonShort>
    fun getAllPokemons(): LiveData<PagingData<PokemonShort>>
    suspend fun fetchPokemonsDetails(pokemonName: String, pokemon: MutableLiveData<Pokemon>)
}