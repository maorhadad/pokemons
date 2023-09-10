package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.util.Log
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hadadas.pokemons.R
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonUpBinding
import com.hadadas.pokemons.games.memorygame.ICard
import com.hadadas.pokemons.utils.GlideApp


class CardUpViewHolder(private val cardUpBinding: CardPokemonUpBinding,
                       private val iClickListener: IItemClickListener?) : CardBaseViewHolder(cardUpBinding.root) {

    private var frontAnimation: Animator? = null
    private var animFlipHide: Animator? = null
    val animSet = AnimatorSet()


    override fun bind(card: ICard, position: Int) {
        Log.d("CardUpViewHolder", "bind: $card")
        cardUpBinding.llCardUp.visibility = View.GONE
        cardUpBinding.llCardBack.visibility = View.VISIBLE
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

    override fun animateFlip(isPreViewFlipped: Boolean, onAnimationStart: () -> Unit, onAnimationDone: () -> Unit) {
        frontAnimation = AnimatorInflater.loadAnimator(cardUpBinding.llCardUp.context, R.animator.card_flip_front_in) as AnimatorSet
        animFlipHide = AnimatorInflater.loadAnimator(cardUpBinding.llCardBack.context, R.animator.card_flip_back) as AnimatorSet
        cardUpBinding.llCardUp.visibility = View.VISIBLE
        cardUpBinding.llCardBack.visibility = View.VISIBLE
        frontAnimation?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.d("frontAnimation", "onAnimationStart")
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d("frontAnimation", "onAnimationEnd")
                animation.removeAllListeners()
                onAnimationDone()
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.d("frontAnimation", "onAnimationCancel")
                animation.removeAllListeners()
                onAnimationDone()
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.d("frontAnimation", "onAnimationRepeat")
            }
        })
        animFlipHide?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.d("animFlipHide", "onAnimationStart")
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d("animFlipHide", "onAnimationEnd")
                animation.removeAllListeners()
                onAnimationDone()
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.d("animFlipHide", "onAnimationCancel")
                animation.removeAllListeners()
                onAnimationDone()
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.d("animFlipHide", "onAnimationRepeat")
            }
        })
        if (isPreViewFlipped){
            flipCardDown()
        }else{
            flipCardUp()
        }
    }
}