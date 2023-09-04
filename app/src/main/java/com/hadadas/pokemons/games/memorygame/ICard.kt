package com.hadadas.pokemons.games.memorygame

import androidx.annotation.IntDef
import com.hadadas.pokemons.abstraction.IPokemon

interface ICard: IPokemon {
    fun isFlipped(): Boolean
    fun setIsFlipped(flipped: Boolean)
    fun isMatched(): Boolean
    fun getCardId(): Int
    @CardsViewTypes
    fun getCardType(): Int

    companion object {

        const val MEMORY_CARD_DOWN = 0
        const val MEMORY_CARD_UP = 1
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(MEMORY_CARD_UP, MEMORY_CARD_DOWN)
    annotation class CardsViewTypes
}