package com.hadadas.pokemons.ui.main

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hadadas.pokemons.databinding.FragmentDetailPokemonBinding
import com.hadadas.pokemons.utils.NameReader

class DetailPokemonFragment : Fragment(), TextToSpeech.OnInitListener {
    companion object {
        fun newInstance() = DetailPokemonFragment()
        const val POKEMON_NAME_KEY = "pokemonName";
    }

    private val viewModel: DetailPokemonViewModel by viewModels()
    private var binding: FragmentDetailPokemonBinding? = null
    private var tts: TextToSpeech? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailPokemonBinding.inflate(inflater)

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.pokemonViewModel = viewModel
        tts = TextToSpeech(context, this)
        val name = arguments?.getString(POKEMON_NAME_KEY)
        if (name == null) {
            Toast
                .makeText(context, "Error. Pokemon name is missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            viewModel.getPokemonDetails(name)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
        binding = null
        viewModel.onDestroyView()
    }

    override fun onInit(status: Int) {
        viewModel.initNameReader(NameReader(tts, context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager))
    }
}