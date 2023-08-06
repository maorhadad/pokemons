package com.hadadas.pokemons.network

import com.hadadas.pokemons.database.DatabasePokemonShort
import com.hadadas.pokemons.domain.PokemonShort
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkPokemonResponse(@Json(name = "count") val count: Int,
                                  @Json(name = "next") val next: String?,
                                  @Json(name = "previous") val previous: String?,
                                  @Json(name = "results") val results: List<NetworkPokemon>)

@JsonClass(generateAdapter = true)
data class NetworkPokemon(@Json(name = "name") val name: String, @Json(name = "url") val url: String)


fun NetworkPokemonResponse.asDomainModel(): List<PokemonShort> {
    return results.map {
        PokemonShort(name = it.name, url = it.url)
    }
}

fun NetworkPokemonResponse.asDatabaseModel(): List<DatabasePokemonShort> {
    return results.map {
        DatabasePokemonShort(name = it.name, url = it.url)
    }
}