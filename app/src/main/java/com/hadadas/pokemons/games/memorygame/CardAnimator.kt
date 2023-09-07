package com.hadadas.pokemons.games.memorygame

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.util.Log
import android.view.animation.AnimationSet
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.games.memorygame.recycler.CardBaseViewHolder

class CardAnimator : DefaultItemAnimator(){
    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ): Boolean = true

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = true

    override fun getSupportsChangeAnimations(): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {

        when (changeFlags) {
            FLAG_CHANGED -> {
                Log.d("CardAnimator", "FLAG_CHANGED")
                payloads.forEach {
                    when (it) {
                        MemoryGameActionType.FLIP_CARD -> {
                            Log.d("CardAnimator", "CardItemHolderInfo(false)")
                            return CardItemHolderInfo(false)
                        }
                        MemoryGameActionType.UNFLIP_CARDS -> {
                            Log.d("CardAnimator", "CardItemHolderInfo(true)")
                            return CardItemHolderInfo(true)
                        }
                    }
                }
            }
            FLAG_INVALIDATED -> {
                Log.d("CardAnimator", "FLAG_INVALIDATED")
            }
            FLAG_REMOVED -> {
                Log.d("CardAnimator", "FLAG_REMOVED")
            }
            FLAG_MOVED -> {
                Log.d("CardAnimator", "FLAG_MOVED")
            }
            FLAG_APPEARED_IN_PRE_LAYOUT -> {
                Log.d("CardAnimator", "FLAG_APPEARED_IN_PRE_LAYOUT")
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun recordPostLayoutInformation(state: RecyclerView.State,
                                             viewHolder: RecyclerView.ViewHolder): ItemHolderInfo {
        return super.recordPostLayoutInformation(state, viewHolder)
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder,
                               newHolder: RecyclerView.ViewHolder,
                               preInfo: ItemHolderInfo,
                               postInfo: ItemHolderInfo): Boolean {

        if (preInfo is CardItemHolderInfo) {
            (newHolder as CardBaseViewHolder).animateFlip(preInfo.isFlipped)
            return true
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }


    class CardItemHolderInfo(val isFlipped: Boolean) : ItemHolderInfo()
}