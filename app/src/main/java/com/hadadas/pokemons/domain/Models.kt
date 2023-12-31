package com.hadadas.pokemons.domain

import com.hadadas.pokemons.abstraction.IPokemon
import com.squareup.moshi.Json


data class PokemonShort(val name: String, val url: String) : IPokemon {
    override fun getPokemonId(): Int {
        val split = url.split("/")
        return split[split.size - 2].toInt()
    }

    override fun getType(): Int = IPokemon.POKEMON_SHORT

    override fun getPokemonName() = name

    override fun getImageURL(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getPokemonId()}.png"
    }
}

data class Pokemon(val id: Int, val name: String, val height: Int, val weight: Int, val sprites: Sprites) : IPokemon{
    override fun getPokemonId(): Int = id

    override fun getType(): Int = IPokemon.POKEMON

    override fun getPokemonName(): String = name

    override fun getImageURL(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonId()}.png"
    }
}

data class Sprites(@Json(name = "front_default") val frontDefault: String?,
                   @Json(name = "back_default") val backDefault: String?)

