package com.hadadas.pokemons.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hadadas.pokemons.domain.Pokemon
import com.hadadas.pokemons.domain.PokemonShort
import com.hadadas.pokemons.domain.Sprites


@Entity
data class DatabasePokemon constructor(@PrimaryKey val id: Int,
                                       val name: String,
                                       val height: Int,
                                       val weight: Int,
                                       val sprites: Sprites)

@Entity
data class DatabasePokemonShort(@PrimaryKey val name: String, val url: String)

fun List<DatabasePokemonShort>.asDomainModel(): List<PokemonShort> {
    return map {
        PokemonShort(name = it.name, url = it.url)
    }
}

fun DatabasePokemon.asDomainModel(): Pokemon {
    return Pokemon(name = name, id = id, height = height, weight = weight, sprites = sprites)
}

