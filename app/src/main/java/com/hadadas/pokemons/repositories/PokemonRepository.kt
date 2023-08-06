package com.hadadas.pokemons.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hadadas.pokemons.abstraction.IRepository
import com.hadadas.pokemons.database.PokemonsDb
import com.hadadas.pokemons.database.asDomainModel
import com.hadadas.pokemons.domain.PokemonShort
import com.hadadas.pokemons.network.Service
import com.hadadas.pokemons.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val database: PokemonsDb) : IRepository {

    val pokemons: LiveData<List<PokemonShort>> = database.pokemonDao
        .getPokemons()
        .map { it.asDomainModel() }

    suspend fun fetchPokemons(limit: Int, offset: Int) {
        withContext(Dispatchers.IO) {
            Log.d("PokemonRepository", "before fetchPokemons")
            val pokemons = Service.PokemonNetwork.pokemonService.getPokemonOffset(limit, offset)
            Log.d("PokemonRepository", "fetchPokemons: $pokemons")
            database.pokemonDao.insertAll(pokemons.asDatabaseModel())
        }
    }
}