package com.hadadas.pokemons.abstraction

import androidx.annotation.IntDef

interface IPokemon {
    fun getPokemonName(): String
    fun getPokemonId(): Int

    fun getImageURL(): String

    @PokemonViewTypes
    fun getType(): Int

    companion object {
        const val POKEMON_SHORT = 0
        const val POKEMON = 1
        const val MEMORY_CARD = 2
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(POKEMON_SHORT, POKEMON, MEMORY_CARD)
    annotation class PokemonViewTypes
}