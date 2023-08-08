package com.hadadas.pokemons.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonApi
import com.hadadas.pokemons.abstraction.IRepository
import com.hadadas.pokemons.database.PokemonsDb
import com.hadadas.pokemons.database.asDomainModel
import com.hadadas.pokemons.domain.Pokemon
import com.hadadas.pokemons.domain.PokemonShort
import com.hadadas.pokemons.network.asDatabaseModel
import com.hadadas.pokemons.network.asShortDatabaseModel
import com.hadadas.pokemons.ui.main.recycler.PokemonsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PokemonRepository(private val database: PokemonsDb, private val pokemonApi: IPokemonApi) : IRepository {

    val shortPokemons: MutableLiveData<List<IPokemon>?> = MutableLiveData()
    suspend fun fetchPokemonsLD(limit: Int, offset: Int) {
        withContext(Dispatchers.IO) {
            shortPokemons.postValue(fetchPokemons(limit, offset))
        }
    }

    suspend fun fetchPokemons(limit: Int, offset: Int): List<PokemonShort> {
        var pokemons = database.pokemonDao.getPokemons(limit, offset)
        Log.d("PokemonRepository", "before fetchPokemons")
        if (pokemons.isEmpty()) {
            Log.d("PokemonRepository", "DB is empty. fetch pokemons from server: $pokemons")
            val pokemonsResponse = pokemonApi.getPokemonOffset(limit, offset)
            pokemons = pokemonsResponse.asShortDatabaseModel()
            database.pokemonDao.insertAll(pokemons)
        }
        return pokemons.asDomainModel()
    }

    fun  getAllPokemons(): LiveData<PagingData<PokemonShort>> {
        return Pager(
                config = PagingConfig(
                        pageSize = 1,
                        enablePlaceholders = false,
                        initialLoadSize = 20
                ),
                pagingSourceFactory = {
                    PokemonsPagingSource(this)
                }
                , initialKey = 0//Offset
        ).liveData
    }

    suspend fun fetchPokemonsDetails(pokemonName: String, pokemon: MutableLiveData<Pokemon>) {
        withContext(Dispatchers.IO) {
            Log.d("PokemonRepository", "get pokemon from DB: $pokemonName")
            var pokemonDetails = database.pokemonDao.getPokemonDetails(pokemonName);
            pokemonDetails?.let { it ->
                Log.d("PokemonRepository", "get pokemon from DB: $it")
                pokemon.postValue(it.asDomainModel())
                return@withContext
            }
            pokemonDetails = pokemonApi
                .getPokemonDetails(pokemonName)
                .asDatabaseModel()
            Log.d("PokemonRepository", "Insert pokemon info: $pokemonDetails")
            database.pokemonDao.insertPokemonInfo(pokemonDetails)
            pokemon.postValue(pokemonDetails.asDomainModel())
        }
    }
}