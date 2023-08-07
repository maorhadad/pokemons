package com.hadadas.pokemons.abstraction

import com.hadadas.pokemons.network.NetworkPokemonResponse
import com.hadadas.pokemons.network.NetworkShortPokemonResponse

interface IPokemonApi {

    suspend fun getPokemonOffset(limit: Int, offset: Int): NetworkShortPokemonResponse

    suspend fun getPokemonDetails(name: String): NetworkPokemonResponse
}