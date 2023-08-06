package com.hadadas.pokemons.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private lateinit var INSTANCE: PokemonsDb
const val DATABASE_NAME = "pokemonsDB"

@Database(entities = [DatabasePokemonShort::class], version = 1)
abstract class PokemonsDb : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

fun getDatabase(context: Context): PokemonsDb {
    synchronized(PokemonsDb::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(context.applicationContext, PokemonsDb::class.java, DATABASE_NAME)
                .build()
        }
    }
    return INSTANCE
}



