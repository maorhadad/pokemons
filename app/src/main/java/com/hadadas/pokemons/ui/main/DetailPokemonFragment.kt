package com.hadadas.pokemons.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hadadas.pokemons.databinding.FragmentDetailPokemonBinding

class DetailPokemonFragment : Fragment() {

    companion object {
        fun newInstance() = DetailPokemonFragment()
    }

    private val viewModel: DetailPokemonViewModel by viewModels()
    private lateinit var binding: FragmentDetailPokemonBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailPokemonBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.pokemonViewModel = viewModel
        val name = arguments?.getString("pokemonName")
        if (name == null) {
            //TODO Handle error// Show toast// Close fragment
        } else {
            viewModel.getPokemonDetails(name)
        }
        return binding.root
    }
}