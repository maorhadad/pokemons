package com.hadadas.pokemons.games.memorygame

import androidx.lifecycle.LiveData

interface IGameMemory {
    suspend fun startGame(numberOfPokemons: Int)
    fun restartGame()
    fun endGame()
    suspend fun flipCardAction(card: Card)
    fun getMemoryGame(): LiveData<MemoryGame?>

    fun getActionResult(): LiveData<ActionResult>
}