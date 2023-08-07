package com.hadadas.pokemons.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class Service {

    interface IPokemonService {
        @GET("pokemon")
        suspend fun getPokemonOffset(@Query("limit") limit: Int, @Query("offset") offset: Int): NetworkShortPokemonResponse
        @GET("pokemon/{name}")
        suspend fun getPokemonDetails(@Path("name") name: String): NetworkPokemonResponse
    }

    object PokemonNetwork {
        private val moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()

        val pokemonService = retrofit.create(IPokemonService::class.java)

    }
}