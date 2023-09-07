package com.hadadas.pokemons.games.memorygame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MemoryGameRepository(private val pokemonRepository: IPokemonRepository) : IGameMemory {

    var memoryGame: MutableLiveData<MemoryGame?> = MutableLiveData()
    private var _memoryGame: MemoryGame? = null
    private var actionResultLD = MutableLiveData<ActionResult>()
    private var currentPlayCards = mutableListOf<Card>()
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

    var isDuringPlay = false
    override suspend fun flipCardAction(card: Card, index: Int) {
        if (currentPlayCards.size >= 2) {
            actionResultLD.postValue(ActionResult(type = MemoryGameActionType.ERROR, message = "Wait for current play to finish"))
        }
        if (card.flipped) {
            actionResultLD.postValue(ActionResult(type = MemoryGameActionType.ERROR, message = "Card already flipped"))
        }
        withContext(Dispatchers.IO) {
            _memoryGame?.board?.apply {
                flipCard(cards, card)
                delay(1700)
                if (currentPlayCards.size >= 2) {
                    val firstCard = currentPlayCards[0]
                    val secondCard = currentPlayCards[1]
                    if (firstCard.pokemon.getPokemonId() == secondCard.pokemon.getPokemonId()) {
                        firstCard.setIsMatched(true)
                        secondCard.setIsMatched(true)
                        val index1 = cards.indexOf(firstCard)
                        val index2 = cards.indexOf(secondCard)
                        flippedCards.add(firstCard)
                        flippedCards.add(secondCard)
                        actionResultLD.postValue(ActionResult(type = MemoryGameActionType.MATCH_CARDS, index1, index2))

                    } else {
                        firstCard.setIsFlipped(false)
                        secondCard.setIsFlipped(false)
                        val index1 = cards.indexOf(firstCard)
                        val index2 = cards.indexOf(secondCard)
                        actionResultLD.postValue(ActionResult(type = MemoryGameActionType.UNFLIP_CARDS, index1, index2))
                    }
                    currentPlayCards.clear()
                }
            }
            checkForEndGame()
        }
    }

    private fun flipCard(cards: MutableList<Card>, card: Card) {
        val index = cards.indexOf(card)
        card.setIsFlipped(true)
        currentPlayCards.add(card)
        actionResultLD.postValue(ActionResult(type = MemoryGameActionType.FLIP_CARD, firstIndex = index))
    }

    private fun checkForEndGame() {
        _memoryGame?.board?.apply {
            if (flippedCards.size == cards.size) {
                _memoryGame?.isGameFinished = true
                actionResultLD.postValue(ActionResult(type = MemoryGameActionType.FINISH_GAME))
            }
        }
    }

    override fun getMemoryGame(): LiveData<MemoryGame?> = memoryGame
    override fun getActionResult(): LiveData<ActionResult> = actionResultLD

    private fun transformToCard(id: Int, pokemon: IPokemon): Card {
        return Card(id, pokemon, false, false)
    }
}