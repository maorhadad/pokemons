package com.hadadas.pokemons.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Query("SELECT * FROM DatabasePokemonShort LIMIT :limit OFFSET :offset")
    fun getPokemonsLD(limit: Int, offset: Int): LiveData<List<DatabasePokemonShort>>

    @Query("SELECT * FROM DatabasePokemonShort LIMIT :limit OFFSET :offset")
    fun getPokemons(limit: Int, offset: Int): List<DatabasePokemonShort>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<DatabasePokemonShort>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonInfo(pokemon: DatabasePokemon)
}