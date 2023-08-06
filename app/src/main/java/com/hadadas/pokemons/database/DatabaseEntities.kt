package com.hadadas.pokemons.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hadadas.pokemons.domain.PokemonShort


@Entity
data class DatabasePokemon constructor(@PrimaryKey val id: Int, val name: String, val height: Int, val weight: Int)

@Entity
data class DatabasePokemonShort(@PrimaryKey val name: String, val url: String)

fun List<DatabasePokemonShort>.asDomainModel(): List<PokemonShort> {
    return map {
        PokemonShort(name = it.name, url = it.url)
    }
}

