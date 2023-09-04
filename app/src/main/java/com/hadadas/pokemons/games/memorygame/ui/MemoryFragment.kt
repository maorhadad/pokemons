package com.hadadas.pokemons.games.memorygame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonDownBinding
import com.hadadas.pokemons.databinding.CardPokemonUpBinding
import com.hadadas.pokemons.databinding.FragmentMemoryBinding
import com.hadadas.pokemons.games.memorygame.*
import com.hadadas.pokemons.games.memorygame.recycler.CardBaseViewHolder
import com.hadadas.pokemons.games.memorygame.recycler.CardDownViewHolder
import com.hadadas.pokemons.games.memorygame.recycler.CardUpViewHolder
import com.hadadas.pokemons.games.memorygame.recycler.PokemonsCardAdapterK
import com.hadadas.pokemons.ui.main.recycler.ViewHolderFactory

class MemoryFragment : Fragment(), IItemClickListener {

    companion object {
        fun newInstance() = MemoryFragment()
    }

    private var pokemonsAdapter: PokemonsCardAdapterK<Card>? = null
    private val viewModel: MemoryGameViewModel by viewModels()
    private var binding: FragmentMemoryBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMemoryBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding?.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding?.viewModel = viewModel

        pokemonsAdapter = PokemonsCardAdapterK(getViewHolderFactory(), this)
        binding?.deviceList?.apply {
            layoutManager = getGridLayoutManager()
            adapter = pokemonsAdapter
        }


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.memoryGameRepository
            .getMemoryGame()
            .observe(viewLifecycleOwner) { memoryGame ->
                memoryGame?.let {
                    val cards = mutableListOf<Card?>()
                    cards.addAll(it.board.cards)
                    pokemonsAdapter?.submitList(cards)
                }
            }
        viewModel.memoryGameRepository
            .getActionResult()
            .observe(viewLifecycleOwner) { actionResult ->
                pokemonsAdapter?.notifyItemChanged(actionResult.index, actionResult.type)
            }
    }


    private fun getGridLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(context, 4)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        return layoutManager
    }

    override fun onStart() {
        super.onStart()
        viewModel.onGameStart()
    }

    override fun onItemClick(pokemon: Any) {
        viewModel.onCardClick(pokemon as Card)
    }

    fun getViewHolderFactory(): ViewHolderFactory<CardBaseViewHolder> {
        return object : ViewHolderFactory<CardBaseViewHolder>() {

            override fun createViewHolder(parent: ViewGroup?,
                                          clickListener: IItemClickListener?,
                                          @ICard.CardsViewTypes viewType: Int): CardBaseViewHolder {
                when (viewType) {
                    ICard.MEMORY_CARD_UP -> {
                        val psb: CardPokemonUpBinding =
                            CardPokemonUpBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                        return CardUpViewHolder(psb, clickListener)
                    }
                    ICard.MEMORY_CARD_DOWN -> {
                        val psb: CardPokemonDownBinding =
                            CardPokemonDownBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                        return CardDownViewHolder(psb, clickListener)
                    }

                    else -> {
                        val psb: CardPokemonDownBinding =
                            CardPokemonDownBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                        return CardDownViewHolder(psb, clickListener)
                    }
                }
            }
        }
    }
}