package com.hadadas.pokemons.ui.main.recycler

import com.hadadas.pokemons.abstraction.IPokemon
import com.hadadas.pokemons.abstraction.IItemClickListener
import com.hadadas.pokemons.databinding.PokemonShortBinding

class PokemonShortViewHolder(private val pokemonShortBinding: PokemonShortBinding,
                             private val iPokemonClickListener: IItemClickListener?) :
        BaseViewHolder(pokemonShortBinding.root) {


    override fun bind(pokemon: IPokemon?, position: Int) {
        pokemonShortBinding.pokemonShort = pokemon
        pokemonShortBinding.clickListener = iPokemonClickListener
    }
}