package com.hadadas.pokemons.abstraction

import androidx.annotation.IntDef

interface IPokemon {
    fun getPokemonName(): String
    fun getPokemonId(): Int

    @PokemonViewTypes
    fun getType(): Int

    companion object {
        const val POKEMON_SHORT = 0
        const val POKEMON = 1
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(POKEMON_SHORT, POKEMON)
    annotation class PokemonViewTypes
}