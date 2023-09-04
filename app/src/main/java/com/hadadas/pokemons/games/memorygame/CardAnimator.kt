package com.hadadas.pokemons.games.memorygame

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.hadadas.pokemons.games.memorygame.recycler.CardBaseViewHolder
import com.hadadas.pokemons.games.memorygame.recycler.CardDownViewHolder
import com.hadadas.pokemons.games.memorygame.recycler.CardUpViewHolder

class CardAnimator : DefaultItemAnimator(){
    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ): Boolean = true

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = true

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        if (changeFlags == FLAG_CHANGED) {
            return when (payloads[0]) {
                MemoryGameActionType.FLIP_CARD -> CardItemHolderInfo(true)
                MemoryGameActionType.UNFLIP_CARD -> CardItemHolderInfo(false)
                else -> super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder,
                               newHolder: RecyclerView.ViewHolder,
                               preInfo: ItemHolderInfo,
                               postInfo: ItemHolderInfo): Boolean {
        val postInfoHolder = postInfo as CardItemHolderInfo
        val holder = if(postInfoHolder.isFlipped){
             newHolder as CardUpViewHolder
        }else{
            newHolder as CardDownViewHolder
        }

        holder.flipCard()
        return true
    }

    class CardItemHolderInfo(val isFlipped: Boolean) : ItemHolderInfo()
}