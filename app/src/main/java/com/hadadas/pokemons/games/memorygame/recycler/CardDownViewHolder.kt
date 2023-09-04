package com.hadadas.pokemons.games.memorygame.recycler

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonDownBinding
import com.hadadas.pokemons.games.memorygame.ICard

class CardDownViewHolder(private val cardDownBinding: CardPokemonDownBinding,
                         private val itemClickListener: IItemClickListener?) :
        CardBaseViewHolder(cardDownBinding.root) {


    override fun bind(card: ICard?, position: Int) {
        cardDownBinding.card = card
        cardDownBinding.clickListener = itemClickListener
    }

    override fun flipCard() {
        cardDownBinding.cardViewDown.apply {
            val flipDown = AnimatorInflater.loadAnimator(cardDownBinding.root.context, com.hadadas.pokemons.R.animator.card_flip_right_out) as AnimatorSet
            flipDown.setTarget(this)
            flipDown.start()
        }
    }
}