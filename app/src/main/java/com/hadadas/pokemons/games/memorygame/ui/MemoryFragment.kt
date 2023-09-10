package com.hadadas.pokemons.games.memorygame.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.CardPokemonUpBinding
import com.hadadas.pokemons.databinding.FragmentMemoryBinding
import com.hadadas.pokemons.games.memorygame.Card
import com.hadadas.pokemons.games.memorygame.CardAnimator
import com.hadadas.pokemons.games.memorygame.ICard
import com.hadadas.pokemons.games.memorygame.MemoryGameActionType
import com.hadadas.pokemons.games.memorygame.recycler.CardBaseViewHolder
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
        binding?.pokaList?.apply {
            adapter = pokemonsAdapter
            itemAnimator = CardAnimator()
            layoutManager = getGridLayoutManager()
        }
        binding?.btRestartGame?.setOnClickListener {
            pokemonsAdapter?.submitList(null)
            viewModel.restartGame()
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.memoryGameRepository
            .getMemoryGame()
            .observe(viewLifecycleOwner) { memoryGame ->
                memoryGame?.let {
                    pokemonsAdapter?.submitList(it.board.cards.toMutableList())

                }
            }
        viewModel.memoryGameRepository
            .getActionResult()
            .observe(viewLifecycleOwner) { actionResult ->
                when (actionResult.type) {
                    MemoryGameActionType.FLIP_CARD -> {
                        Log.w("MemoryFragment", "FLIP_CARD ${actionResult.firstIndex} ")
                        pokemonsAdapter?.notifyItemChanged(actionResult.firstIndex, actionResult.type)
                    }
                    MemoryGameActionType.UNFLIP_CARDS -> {
                        Handler(requireContext().mainLooper).postDelayed({
                            Log.w("MemoryFragment", "UNFLIP_CARDS ${actionResult.firstIndex} ${actionResult.secondIndex}")
                            pokemonsAdapter?.notifyItemChanged(actionResult.firstIndex, actionResult.type)
                            pokemonsAdapter?.notifyItemChanged(actionResult.secondIndex, actionResult.type)
                        }, 1000)
                    }
                    MemoryGameActionType.MATCH_CARDS -> {
                        Toast
                            .makeText(requireContext(), "Show Pitsotsation animation", Toast.LENGTH_SHORT)
                            .show()
                    }
                    MemoryGameActionType.FINISH_GAME -> {
                        Toast
                            .makeText(requireContext(), "Show a bigger Pitsotsation animation", Toast.LENGTH_SHORT)
                            .show() //TODO show animation
                    }
                    MemoryGameActionType.RESTART_GAME -> {
                        Toast
                            .makeText(requireContext(), "Restart Game", Toast.LENGTH_SHORT)
                            .show()
                    }
                    MemoryGameActionType.ERROR -> {
                        Toast
                            .makeText(requireContext(), actionResult.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        pokemonsAdapter?.notifyDataSetChanged()
                    }
                }
            }
    }


    private fun getGridLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(context, calculateSpanCount(requireContext(), 120))
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        return layoutManager
    }

    private fun calculateSpanCount(context: Context, viewSizeDp: Int): Int {
        // Get the screen width in pixels
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenWidthPixels = displayMetrics.widthPixels

        // Convert view size from dp to pixels
        val density = context.resources.displayMetrics.density
        val viewSizePixels = (viewSizeDp * density).toInt()

        // Calculate the span count based on screen width and view size
        var spanCount = screenWidthPixels / viewSizePixels

        // Ensure the minimum span count is 1
        spanCount = Math.max(spanCount, 1)
        Log.d("MemoryFragment", "calculateSpanCount: $spanCount")
        return spanCount
    }

    override fun onStart() {
        super.onStart()
        viewModel.onGameStart()
    }

    override fun onItemClick(pokemonCard: Any) {
        pokemonsAdapter?.currentList?.let {
            viewModel.onCardClick(pokemonCard as Card, it.indexOf(pokemonCard))
        }
    }

    private fun getViewHolderFactory(): ViewHolderFactory<CardBaseViewHolder> {
        return object : ViewHolderFactory<CardBaseViewHolder>() {

            override fun createViewHolder(parent: ViewGroup?,
                                          clickListener: IItemClickListener?,
                                          @ICard.CardsViewTypes viewType: Int): CardBaseViewHolder {
                val psb: CardPokemonUpBinding =
                    CardPokemonUpBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                CardUpViewHolder(psb, clickListener)
//                return when (viewType) {
//                    ICard.MEMORY_CARD_UP -> {
//                        val psb: CardPokemonUpBinding =
//                            CardPokemonUpBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
//                        CardUpViewHolder(psb, clickListener)
//                    }
//                    else -> {
//                        val psb: CardPokemonDownBinding =
//                            CardPokemonDownBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
//                        CardDownViewHolder(psb, clickListener)
//                    }
//                }
                return CardUpViewHolder(psb, clickListener)
            }
        }
    }
}