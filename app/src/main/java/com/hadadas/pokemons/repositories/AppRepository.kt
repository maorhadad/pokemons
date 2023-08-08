package com.hadadas.pokemons.repositories

import android.content.Context
import com.hadadas.pokemons.abstraction.IRepository
import com.hadadas.pokemons.database.getDatabase
import com.hadadas.pokemons.network.PokemonService

class AppRepository(context: Context) : IRepository {
    private val pokemonDb = getDatabase(context.applicationContext)
    private val pokemonApi = PokemonService()
    override val pokemonRepository = PokemonRepository(pokemonDb, pokemonApi)
}