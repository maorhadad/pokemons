package com.hadadas.pokemons.games.memorygame

import com.hadadas.pokemons.abstraction.IPokemon

data class MemoryGame(val players: List<Player>, val board: Board, var isGameFinished: Boolean)

data class Player(val id: Int, val name: String, val score: Int)

data class Board(val cards: MutableList<Card>, val flippedCards: MutableList<Card>, val isBoardFinished: Boolean)

data class Card(val id: Int, val pokemon: IPokemon, var flipped: Boolean, var matched: Boolean): ICard{
    override fun isFlipped(): Boolean {
        return flipped
    }

    override fun setIsFlipped(flipped: Boolean) {
        this.flipped = flipped
    }

    override fun setIsMatched(matched: Boolean) {
        this.matched = matched
    }

    override fun isMatched(): Boolean {
        return matched
    }

    override fun getCardId(): Int {
        return id
    }

    override fun getCardType(): Int {
        return if (flipped) ICard.MEMORY_CARD_UP else ICard.MEMORY_CARD_DOWN
    }

    override fun getPokemonName(): String {
       return pokemon.getPokemonName()
    }

    override fun getPokemonId(): Int {
        return pokemon.getPokemonId()
    }

    override fun getImageURL(): String {
        return pokemon.getImageURL()
    }

    override fun getType(): Int {
        return pokemon.getType()
    }
}

data class MemoryGameResult(val winner: Player, val players: List<Player>)

data class MemoryGameError(val message: String)

data class MemoryGameEvent(val type: MemoryGameEventType, val data: MemoryGameEventData)

enum class MemoryGameEventType {
    START_GAME, RESTART_GAME, END_GAME, FLIP_CARD, MATCH_CARDS, UNFLIP_CARDS, FINISH_BOARD, FINISH_GAME, ERROR
}

data class MemoryGameEventData(val players: List<Player>, val board: Board, val error: MemoryGameError)

data class MemoryGameAction(val type: MemoryGameActionType, val data: MemoryGameActionData)

enum class MemoryGameActionType {
    START_GAME, RESTART_GAME, END_GAME, FLIP_CARD, MATCH_CARDS, UNFLIP_CARDS, FINISH_BOARD, FINISH_GAME, ERROR
}

data class MemoryGameActionData(val players: List<Player>, val board: Board, val error: MemoryGameError)

data class MemoryGameCommand(val type: MemoryGameCommandType, val data: MemoryGameCommandData)

enum class MemoryGameCommandType {
    START_GAME, RESTART_GAME, END_GAME, FLIP_CARD, MATCH_CARDS, UNFLIP_CARDS, FINISH_BOARD, FINISH_GAME, ERROR
}

data class MemoryGameCommandData(val players: List<Player>, val board: Board, val error: MemoryGameError)

data class ActionResult(val type: MemoryGameActionType, val firstIndex: Int = -1, val secondIndex: Int = -1, val message: String = "")