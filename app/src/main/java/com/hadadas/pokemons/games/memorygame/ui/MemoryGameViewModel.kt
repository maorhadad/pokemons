package com.hadadas.pokemons.games.memorygame.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hadadas.pokemons.abstraction.IRepositoryAccess
import com.hadadas.pokemons.games.memorygame.Card
import kotlinx.coroutines.launch
import java.io.IOException

class MemoryGameViewModel(application: Application) : AndroidViewModel(application) {

    val memoryGameRepository = (application as IRepositoryAccess).getRepository().memoryGameRepository

    public fun onGameStart(numberOfPokemons: Int = 10) {
        viewModelScope.launch {
            try {
                memoryGameRepository.startGame(numberOfPokemons)
            } catch (networkError: IOException) {
                Toast
                    .makeText(getApplication(), "Network error", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    fun onCardClick(card: Card) {
        viewModelScope.launch {
            memoryGameRepository.flipCardAction(card)
        }
    }
}