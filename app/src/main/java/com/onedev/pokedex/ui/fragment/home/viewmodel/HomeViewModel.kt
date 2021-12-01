package com.onedev.pokedex.ui.fragment.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.onedev.pokedex.core.domain.usecase.PokemonUseCase

class HomeViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    fun getPokemon(limit: Int) = pokemonUseCase.getPokemon(limit).asLiveData()
    fun getPokemonByName(pokemonName: String) = pokemonUseCase.getPokemonByName(pokemonName).asLiveData()

}