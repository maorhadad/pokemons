package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonUpBinding
import com.hadadas.pokemons.games.memorygame.ICard


class CardUpViewHolder(private val cardUpBinding: CardPokemonUpBinding,
                       private val iClickListener: IItemClickListener?) : CardBaseViewHolder(cardUpBinding.root) {

    private var frontAnimation: Animator? = null
    private var animFlipHide: Animator? = null


    override fun bind(card: ICard?, position: Int) {
        cardUpBinding.card = card
        cardUpBinding.clickListener = iClickListener
    }

    private fun flipCardUp() {
        frontAnimation?.setTarget(cardUpBinding.llCardUp)
        animFlipHide?.setTarget(cardUpBinding.llCardBack)
        frontAnimation?.start()
        animFlipHide?.start()
    }

    private fun flipCardDown() {
        frontAnimation?.setTarget(cardUpBinding.llCardBack)
        animFlipHide?.setTarget(cardUpBinding.llCardUp)
        animFlipHide?.start()
        frontAnimation?.start()
    }

    override fun animateFlip(isPreViewFlipped: Boolean) {
        frontAnimation = AnimatorInflater.loadAnimator(cardUpBinding.root.context, com.hadadas.pokemons.R.animator.card_flip_front_in) as AnimatorSet
        animFlipHide = AnimatorInflater.loadAnimator(cardUpBinding.root.context, com.hadadas.pokemons.R.animator.card_flip_back) as AnimatorSet
        frontAnimation?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                animation.removeAllListeners()
            }

            override fun onAnimationCancel(animation: Animator) {
                animation.removeAllListeners()
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animFlipHide?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                animation.removeAllListeners()
            }

            override fun onAnimationCancel(animation: Animator) {
                animation.removeAllListeners()
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
        if (isPreViewFlipped){
            flipCardDown()
        }else{
            flipCardUp()
        }
    }
}