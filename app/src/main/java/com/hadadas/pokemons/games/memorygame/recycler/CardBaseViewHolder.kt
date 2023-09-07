package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.Animator
import android.util.Log
import android.view.View
import com.hadadas.pokemons.abstraction.ABVIewHolder
import com.hadadas.pokemons.games.memorygame.ICard

abstract class CardBaseViewHolder(itemView: View) : ABVIewHolder(itemView) {

    abstract fun bind(card: ICard?, position: Int)

    abstract fun animateFlip(isPreViewFlipped: Boolean)
}