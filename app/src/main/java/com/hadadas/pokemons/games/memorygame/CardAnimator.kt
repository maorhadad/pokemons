package com.hadadas.pokemons.games.memorygame

import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.games.memorygame.recycler.CardBaseViewHolder

class CardAnimator : DefaultItemAnimator(){

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>
    ) = true

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder)= true


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
                        MemoryGameActionType.FLIP_CARD_UP -> {
                            Log.d("CardAnimator", "CardItemHolderPreviousInfo(false)")
                            return CardItemHolderPreviousInfo(false)
                        }
                        MemoryGameActionType.FLIP_CARD_DOWN -> {
                            Log.d("CardAnimator", "FLIP_CARD_DOWN CardItemHolderPreviousInfo(true)")
                            return CardItemHolderPreviousInfo(true)
                        }
                        MemoryGameActionType.UNFLIP_CARDS -> {
                            Log.d("CardAnimator", "CardItemHolderPreviousInfo(true)")
                            return CardItemHolderPreviousInfo(true)
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

        if (preInfo is CardItemHolderPreviousInfo) {
            (newHolder as CardBaseViewHolder).animateFlip(preInfo.isFlipped, onAnimationStart = {
                dispatchAnimationStarted(newHolder)
            }, onAnimationDone = {
                dispatchAnimationFinished(newHolder)
            })
            return true
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }




    class CardItemHolderPreviousInfo(val isFlipped: Boolean) : ItemHolderInfo()



}