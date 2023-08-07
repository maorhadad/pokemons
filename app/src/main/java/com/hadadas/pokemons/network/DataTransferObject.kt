package com.hadadas.pokemons.network

import com.hadadas.pokemons.database.DatabasePokemon
import com.hadadas.pokemons.database.DatabasePokemonShort
import com.hadadas.pokemons.domain.Sprites
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkShortPokemonResponse(@Json(name = "count") val count: Int,
                                       @Json(name = "next") val next: String?,
                                       @Json(name = "previous") val previous: String?,
                                       @Json(name = "results") val results: List<NetworkShortPokemon>)

@JsonClass(generateAdapter = true)
data class NetworkShortPokemon(@Json(name = "name") val name: String, @Json(name = "url") val url: String)


fun NetworkShortPokemonResponse.asShortDatabaseModel(): List<DatabasePokemonShort> {
    return results.map {
        DatabasePokemonShort(name = it.name, url = it.url)
    }
}

@JsonClass(generateAdapter = true)
data class NetworkPokemonResponse(@Json(name = "id") val id: Int,
                                  @Json(name = "name") val name: String,
                                  @Json(name = "height") val height: Int,
                                  @Json(name = "weight") val weight: Int,
                                  @Json(name = "sprites") val sprites: Sprites)

fun NetworkPokemonResponse.asDatabaseModel(): DatabasePokemon {
    return DatabasePokemon(id = id, name = name, height = height, weight = weight, sprites = sprites)
}