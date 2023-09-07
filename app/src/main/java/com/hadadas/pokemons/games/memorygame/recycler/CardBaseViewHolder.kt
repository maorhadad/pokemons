package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.Animator
import android.util.Log
import android.view.View
import com.hadadas.pokemons.abstraction.ABVIewHolder
import com.hadadas.pokemons.games.memorygame.ICard

abstract class CardBaseViewHolder(itemView: View) : ABVIewHolder(itemView), Animator.AnimatorListener {

    abstract fun bind(card: ICard?, position: Int)
    override fun onAnimationStart(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationStart")
    }

    override fun onAnimationEnd(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationEnd")
        animation.removeAllListeners()
    }

    override fun onAnimationCancel(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationCancel")
        animation.removeAllListeners()
    }

    override fun onAnimationRepeat(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationRepeat")

    }

    abstract fun animateFlip(isPreViewFlipped: Boolean)
}