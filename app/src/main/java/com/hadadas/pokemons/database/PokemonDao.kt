package com.hadadas.pokemons.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Query("select * from DatabasePokemonShort")
    fun getPokemons(): LiveData<List<DatabasePokemonShort>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<DatabasePokemonShort>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonInfo(pokemon: DatabasePokemon)
}