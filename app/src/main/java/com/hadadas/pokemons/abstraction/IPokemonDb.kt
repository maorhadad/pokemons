package com.hadadas.pokemons.abstraction

import androidx.lifecycle.LiveData
import com.hadadas.pokemons.database.DatabasePokemon
import com.hadadas.pokemons.database.DatabasePokemonShort

interface IPokemonDb {

    fun getPokemonsLD(limit: Int, offset: Int): LiveData<List<DatabasePokemonShort>>

    fun getPokemons(limit: Int, offset: Int): List<DatabasePokemonShort>

    fun insertAll(pokemons: List<DatabasePokemonShort>)

    fun insertPokemonInfo(pokemon: DatabasePokemon)

    fun getPokemonDetails(pokemonName: String): DatabasePokemon?
}