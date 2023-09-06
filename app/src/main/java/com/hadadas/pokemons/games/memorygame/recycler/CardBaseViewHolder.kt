package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.Animator
import android.util.Log
import android.view.View
import com.hadadas.pokemons.abstraction.ABVIewHolder
import com.hadadas.pokemons.games.memorygame.ICard

abstract class CardBaseViewHolder(itemView: View) : ABVIewHolder(itemView), Animator.AnimatorListener {

    public var anim: Animator? = null
    abstract fun bind(card: ICard?, position: Int)
    abstract fun flipCard()
    override fun onAnimationStart(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationStart")
    }

    override fun onAnimationEnd(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationEnd")
    }

    override fun onAnimationCancel(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationCancel")
    }

    override fun onAnimationRepeat(animation: Animator) {
        Log.d("CardBaseViewHolder", "onAnimationRepeat")
    }
}