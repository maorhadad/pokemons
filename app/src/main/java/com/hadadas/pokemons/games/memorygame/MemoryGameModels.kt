package com.hadadas.pokemons.games.memorygame

import com.hadadas.pokemons.abstraction.IPokemon

data class MemoryGame(val players: List<Player>, val board: Board, val isGameFinished: Boolean)

data class Player(val id: Int, val name: String, val score: Int)

data class Board(val cards: MutableList<Card>, val flippedCards: MutableList<Card>, val isBoardFinished: Boolean)

data class Card(val id: Int, val pokemon: IPokemon, var isFlipped: Boolean, val isMatched: Boolean)

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