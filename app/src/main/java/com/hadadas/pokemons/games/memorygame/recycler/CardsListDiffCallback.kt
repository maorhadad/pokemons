package com.hadadas.pokemons.games.memorygame.recycler

import androidx.recyclerview.widget.DiffUtil
import com.hadadas.pokemons.games.memorygame.ICard
import com.hadadas.pokemons.games.memorygame.MemoryGameActionType

class CardsListDiffCallback<T : ICard> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getCardId() == newItem.getCardId()
                && oldItem.getPokemonId() == newItem.getPokemonId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.isFlipped() == newItem.isFlipped()
                && oldItem.isMatched() == newItem.isMatched()
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return when {
            !oldItem.isFlipped() && newItem.isFlipped() -> MemoryGameActionType.FLIP_CARD_UP
            oldItem.isFlipped() && newItem.isFlipped() -> MemoryGameActionType.UNFLIP_CARDS
            else -> null
        }
    }

}