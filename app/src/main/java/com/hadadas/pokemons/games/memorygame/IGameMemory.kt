package com.hadadas.pokemons.games.memorygame

interface IGameMemory {
    suspend fun startGame(numberOfPokemons: Int)
    fun restartGame()
    fun endGame()
    fun flipCardAction(userId: Int, card: Card)
}