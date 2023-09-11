package com.hadadas.pokemons.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.hadadas.pokemons.R
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.databinding.FragmentMainBinding
import com.hadadas.pokemons.domain.PokemonShort
import com.hadadas.pokemons.ui.main.recycler.PokemonsAdapterK

class MainFragment : Fragment(), IItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var pokemonsAdapter: PokemonsAdapterK<PokemonShort>? = null
    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentMainBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding?.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding?.viewModel = viewModel

        pokemonsAdapter = PokemonsAdapterK(viewModel.getViewHolderFactory(), this)
        binding?.pokaList?.apply {
            layoutManager = getGridLayoutManager()
            adapter = pokemonsAdapter
        }


        pokemonsAdapter?.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) binding?.progressDialog?.isVisible =
                true
            else {
                binding?.progressDialog?.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    requireContext().let { context ->
                        Toast
                            .makeText(context, it.error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateMenu()
        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            pokemons?.let {
                pokemonsAdapter?.submitData(lifecycle, it)
            }
        }
    }

    override fun onItemClick(pokemon: Any) {
        val action = MainFragmentDirections
            .actionMainFragmentToDetailPokemonFragment((pokemon as IPokemon).getPokemonName())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
        binding = null
    }

    private fun getGridLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pokemonsAdapter?.getItemViewType(position)) {
                    IPokemon.POKEMON_SHORT -> 1
                    IPokemon.POKEMON -> 2
                    else -> -1
                }
            }
        }
        return layoutManager
    }

    private fun inflateMenu() {
        val navController = findNavController()
        binding?.toolbar?.setupWithNavController(navController)
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_game -> {
                    val action = MainFragmentDirections.actionMainFragmentToMemoryFragment()
                    findNavController().navigate(action)
                    true
                }
                R.id.license -> {
                    // loadTasks(true)
                    true
                }
                else -> false
            }
        }
    }
}