package com.hadadas.pokemons.ui.main.recycler

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hadadas.pokemons.abstraction.IPokemonRepository
import com.hadadas.pokemons.domain.PokemonShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonsPagingSource(private val pokemonRepository: IPokemonRepository) : PagingSource<Int, PokemonShort>() {
    private val delta = 20
    override fun getRefreshKey(state: PagingState<Int, PokemonShort>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorOffset = state.closestPageToPosition(anchorPosition)
            anchorOffset?.prevKey?.plus(delta) ?: anchorOffset?.nextKey?.minus(delta)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonShort> {
        return try {
            withContext(Dispatchers.IO) {
                val offset = params.key ?: 0
                val pokemons = pokemonRepository.fetchPokemons(delta, offset)
                val prevKey = if (offset == 0) null else offset + delta
                val nextKey = if (pokemons.isNotEmpty()) offset + delta else null

                LoadResult.Page(pokemons, prevKey, nextKey)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}