package com.hadadas.pokemons.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IPokemonClickListener
import com.hadadas.pokemons.databinding.FragmentMainBinding
import com.hadadas.pokemons.domain.PokemonShort
import com.hadadas.pokemons.ui.main.recycler.PokemonsAdapterK

class MainFragment : Fragment(), IPokemonClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var pokemonsAdapter: PokemonsAdapterK<PokemonShort>? = null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        setupRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            pokemons?.apply {
                pokemonsAdapter?.submitList(pokemons)
            }
        }
    }

    private fun setupRecycler() {
        pokemonsAdapter = PokemonsAdapterK(viewModel.getViewHolderFactory(), this)

        binding.deviceList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonsAdapter
        }
    }

    override fun onPokemonClick(pokemon: IPokemon) {
        Toast
            .makeText(context, "Clicked on ${pokemon.getPokemonName()}", Toast.LENGTH_SHORT)
            .show()
    }
}