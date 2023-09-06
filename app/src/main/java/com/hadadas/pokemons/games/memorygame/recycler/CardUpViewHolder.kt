package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonUpBinding
import com.hadadas.pokemons.games.memorygame.ICard


class CardUpViewHolder(private val cardUpBinding: CardPokemonUpBinding,
                       private val iClickListener: IItemClickListener?) : CardBaseViewHolder(cardUpBinding.root) {

    init {
        anim =
            AnimatorInflater.loadAnimator(cardUpBinding.root.context, com.hadadas.pokemons.R.animator.card_flip_right_in) as AnimatorSet
        anim?.addListener(this)
    }

    override fun bind(card: ICard?, position: Int) {
        anim?.setTarget(cardUpBinding.cardViewUp)
        cardUpBinding.card = card
        cardUpBinding.clickListener = iClickListener
        anim?.start()
    }

    override fun flipCard() {

    }
}