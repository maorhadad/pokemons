package com.hadadas.pokemons.games.memorygame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoryGameRepository(private val pokemonRepository: IPokemonRepository) : IGameMemory {

    var memoryGame: MutableLiveData<MemoryGame?> = MutableLiveData()
    private var _memoryGame: MemoryGame? = null
    private var  actionResultLD= MutableLiveData<ActionResult>()

    override suspend fun startGame(numberOfPokemons: Int) {
         try {
            withContext(Dispatchers.IO) {
                val offset = (0..950).random()
                var players: List<Player> = listOf(Player(1, "Player 1", 0))
                var cardId = 0

                val cards: MutableList<Card> = pokemonRepository
                    .fetchPokemons(numberOfPokemons, offset)
                    .map {
                        cardId += 1
                        transformToCard(cardId, it)
                    }
                    .toMutableList()
                val cards2 = mutableListOf<Card>()
                cards.forEach {
                    cardId += 1
                    cards2.add(transformToCard(cardId, it.pokemon))
                }
                cards.addAll(cards2)
                val board = Board(cards = cards, flippedCards = mutableListOf(), isBoardFinished = false)
                _memoryGame = MemoryGame(players = players, board = board, isGameFinished = false)
                memoryGame.postValue(_memoryGame)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun restartGame() {
        TODO("Not yet implemented")
    }

    override fun endGame() {
        TODO("Not yet implemented")
    }

    override fun flipCardAction(card: Card) {
        _memoryGame?.board?.let {
            val index = it.cards.indexOf(card)
            card.setIsFlipped(!card.flipped)
            val type = if(card.isFlipped()) MemoryGameActionType.FLIP_CARD else MemoryGameActionType.UNFLIP_CARD
            actionResultLD.postValue(ActionResult( type , index))
        }
        memoryGame.postValue(_memoryGame)
    }

    override fun getMemoryGame(): LiveData<MemoryGame?> = memoryGame
    override fun getActionResult(): LiveData<ActionResult> = actionResultLD


    private fun transformToCard(id: Int, pokemon: IPokemon): Card {
        return Card(id, pokemon, false, false)
    }
}