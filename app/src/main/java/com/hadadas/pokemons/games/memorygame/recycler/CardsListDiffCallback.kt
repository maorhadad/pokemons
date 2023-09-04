package com.hadadas.pokemons.games.memorygame.recycler

import androidx.recyclerview.widget.DiffUtil
import com.hadadas.pokemons.games.memorygame.ICard

class CardsListDiffCallback<T : ICard> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getCardId() == newItem.getCardId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getCardId() == newItem.getCardId()
                && oldItem.isFlipped() == newItem.isFlipped()
    }

}