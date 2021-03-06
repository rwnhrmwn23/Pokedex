package com.onedev.pokedex.ui.fragment.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.core.domain.usecase.PokemonUseCase

class DetailViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    fun getPokemonById(id: Int) = pokemonUseCase.getPokemonById(id).asLiveData()
    fun updatePokemonFavorite(pokemon: Pokemon, state: Boolean) = pokemonUseCase.updatePokemonFavorite(pokemon, state)

}