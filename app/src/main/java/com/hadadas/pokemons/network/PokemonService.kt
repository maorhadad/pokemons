package com.hadadas.pokemons.network

import com.hadadas.pokemons.abstraction.IPokemonApi
import com.hadadas.pokemons.network.PokemonService.PokemonNetwork.service
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class PokemonService : IPokemonApi {

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

        val service = retrofit.create(IPokemonService::class.java)

    }

    override suspend fun getPokemonOffset(limit: Int, offset: Int) =  service.getPokemonOffset(limit, offset)

    override suspend fun getPokemonDetails(name: String) = service.getPokemonDetails(name)
}