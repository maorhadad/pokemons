package com.hadadas.pokemons.games.memorygame

import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonRepository

class MemoryGameRepository(private val pokemonRepository: IPokemonRepository) : IGameMemory {

    private lateinit var memoryGame: MemoryGame


    override suspend fun startGame(numberOfPokemons: Int) {
        val offset = (0..950).random()
        var players: List<Player> = listOf(Player(1, "Player 1", 0))
        var cardId = 0
        val cards: MutableList<Card> = pokemonRepository
            .fetchPokemons(numberOfPokemons, offset)
            .map {
                cardId += 1
                transformToCard(cardId, it)
            }.toMutableList()
        cards.forEach {
            cardId += 1
            cards.add(transformToCard(cardId, it.pokemon))
        }
        val board = Board(cards = cards, flippedCards = mutableListOf(), isBoardFinished = false)
        memoryGame = MemoryGame(players = players, board = board, isGameFinished = false)
    }

    override fun restartGame() {
        TODO("Not yet implemented")
    }

    override fun endGame() {
        TODO("Not yet implemented")
    }

    override fun flipCardAction(userId: Int, card: Card) {
        memoryGame.board.cards[card.id].isFlipped = !card.isFlipped

        if(!card.isFlipped) {
            memoryGame.board.flippedCards.add(card)
        } else {
            memoryGame.board.flippedCards.remove(card)
        }
    }

    private fun transformToCard(id: Int, pokemon: IPokemon): Card {
        return Card(id, pokemon, false, false)
    }
}