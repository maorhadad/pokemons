package com.hadadas.pokemons.games.memorygame

import androidx.lifecycle.LiveData
import com.hadadas.pokemons.utils.Event

interface IGameMemory {
    suspend fun startGame(numberOfPokemons: Int)
    suspend fun restartGame()
    fun endGame()
    suspend fun flipCardAction(card: Card, index : Int)
    fun getMemoryGame(): LiveData<MemoryGame?>

    fun getActionResult(): LiveData<Event<ActionResult>>

    suspend fun validateCards()

}