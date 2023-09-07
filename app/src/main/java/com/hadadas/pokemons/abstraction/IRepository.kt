package com.hadadas.pokemons.abstraction

import com.hadadas.pokemons.games.memorygame.IGameMemory

interface IRepository {
    val pokemonRepository: IPokemonRepository
    val memoryGameRepository: IGameMemory
}