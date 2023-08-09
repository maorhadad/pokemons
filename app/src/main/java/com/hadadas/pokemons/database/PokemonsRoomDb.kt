package com.hadadas.pokemons.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hadadas.pokemons.abstraction.IPokemonDb


private lateinit var INSTANCE: PokemonsDb
const val DATABASE_NAME = "pokemonsDB"

@Database(entities = [DatabasePokemonShort::class, DatabasePokemon::class], version = 2)
@TypeConverters(SpritesTypeConverter::class)
abstract class PokemonsDb : RoomDatabase(), IPokemonDb {
    abstract val pokemonDao: PokemonDao
    override fun getPokemonsLD(limit: Int, offset: Int) = pokemonDao.getPokemonsLD(limit, offset)

    override fun getPokemons(limit: Int, offset: Int) = pokemonDao.getPokemons(limit, offset)

    override fun insertAll(pokemons: List<DatabasePokemonShort>) = pokemonDao.insertAll(pokemons)

    override fun insertPokemonInfo(pokemon: DatabasePokemon) = pokemonDao.insertPokemonInfo(pokemon)

    override fun getPokemonDetails(pokemonName: String) = pokemonDao.getPokemonDetails(pokemonName)
}

fun getDatabase(context: Context): IPokemonDb {
    synchronized(PokemonsDb::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(context.applicationContext, PokemonsDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}



