package com.hadadas.pokemons.games.memorygame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadadas.pokemons.BuildConfig
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoryGameRepository(private val pokemonRepository: IPokemonRepository) : IGameMemory {

    private var memoryGame: MutableLiveData<MemoryGame?> = MutableLiveData()
    private var _memoryGame: MemoryGame? = null
    private var actionResultLD = MutableLiveData<ActionResult>()
    private var currentPlayCards = mutableListOf<Card>()
    private var numberOfPokemons = 0

    override suspend fun startGame(_numberOfPokemons: Int) {
        try {
            withContext(Dispatchers.IO) {
                numberOfPokemons = _numberOfPokemons
                _memoryGame = generateGameBoard(numberOfPokemons)
                memoryGame.postValue(_memoryGame)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun generateGameBoard(numberOfPokemons: Int): MemoryGame {
        val offset = (0..950).random()
        val players: List<Player> = listOf(Player(1, "Player 1", 0))
        var cardId = 0
        currentPlayCards.clear()

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
        if (!BuildConfig.DEBUG) {
            cards.shuffle()
        }
        val board = Board(cards = cards, flippedCards = mutableListOf(), isBoardFinished = false)
        return MemoryGame(players = players, board = board, isGameFinished = false)
    }

    override suspend fun restartGame() {
        try {
            withContext(Dispatchers.IO) {
                Log.d("MemoryGameRepository", "restartGame: ")
                actionResultLD.postValue(ActionResult(type = MemoryGameActionType.RESTART_GAME, message = "Game started"))
                _memoryGame = generateGameBoard(numberOfPokemons)
                memoryGame.postValue(_memoryGame)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun endGame() {
        TODO("Not yet implemented")
    }

    override suspend fun flipCardAction(card: Card, index: Int) {
        _memoryGame?.board?.apply {
            if (currentPlayCards.size >= 2) {
                Log.d("MemoryGameRepository", "flipCardAction: currentPlayCards.size >= 2. Retruning")
                actionResultLD.postValue(ActionResult(type = MemoryGameActionType.ERROR, message = "Wait for current play to finish"))
                return@apply
            }
            if (card.flipped) {
                Log.d("MemoryGameRepository", "flipCardAction: card.flipped. Retruning")
                actionResultLD.postValue(ActionResult(type = MemoryGameActionType.ERROR, message = "Card already flipped"))
                return@apply
            }
            flipCard(cards, card)
            checkUserPlay(cards, flippedCards)
            checkForEndGame(cards, flippedCards)
        }
    }

    private fun flipCard(cards: MutableList<Card>, card: Card) {
        Log.d("MemoryGameRepository", "flipCard: card: ${card.id}")
        val index = cards.indexOf(card)
        card.setIsFlipped(true)
        currentPlayCards.add(card)
        actionResultLD.value = ActionResult(type = MemoryGameActionType.FLIP_CARD, firstIndex = index)
    }

    private fun checkUserPlay(cards: MutableList<Card>, flippedCards: MutableList<Card>) {
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
                actionResultLD.value = ActionResult(type = MemoryGameActionType.MATCH_CARDS, index1, index2)

            } else {
                firstCard.setIsFlipped(false)
                secondCard.setIsFlipped(false)
                val index1 = cards.indexOf(firstCard)
                val index2 = cards.indexOf(secondCard)
                Log.d("MemoryGameRepository", "checkUserPlay: ${firstCard.id} ${secondCard.id} " + "un flip cards at index: index1: ${index1} index2: $index2")
                actionResultLD.value = (ActionResult(type = MemoryGameActionType.UNFLIP_CARDS, index1, index2))
            }
            Log.w("MemoryGameRepository", "checkUserPlay: currentPlayCards.clear()")
            currentPlayCards.clear()
        }
    }


    private fun checkForEndGame(cards: MutableList<Card>, flippedCards: MutableList<Card>) {
        if (flippedCards.size == cards.size) {
            _memoryGame?.isGameFinished = true
            actionResultLD.postValue(ActionResult(type = MemoryGameActionType.FINISH_GAME))
        }
    }

    override fun getMemoryGame(): LiveData<MemoryGame?> = memoryGame
    override fun getActionResult(): LiveData<ActionResult> = actionResultLD

    private fun transformToCard(id: Int, pokemon: IPokemon): Card {
        return Card(id, pokemon, false, false)
    }
}