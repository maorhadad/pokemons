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

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = false

    override fun getSupportsChangeAnimations(): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        if (changeFlags == FLAG_CHANGED && payloads.isNotEmpty()) {
            val holder =  when (payloads[0]) {
                MemoryGameActionType.FLIP_CARD -> CardItemHolderInfo(false)
                MemoryGameActionType.UNFLIP_CARDS -> CardItemHolderInfo(true)
                else -> super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
            }
            return holder
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder,
                               newHolder: RecyclerView.ViewHolder,
                               preInfo: ItemHolderInfo,
                               postInfo: ItemHolderInfo): Boolean {

        if(preInfo is CardItemHolderInfo){
            newHolder as CardBaseViewHolder
            newHolder.flipCard()
            return true
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    class CardItemHolderInfo(val isFlipped: Boolean) : ItemHolderInfo()
}