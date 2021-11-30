package com.onedev.pokedex.ui.fragment.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.onedev.pokedex.core.data.source.PokemonRepository
import com.onedev.pokedex.core.domain.usecase.PokemonUseCase

class DetailViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    fun getPokemonDetail(id: Int) = pokemonUseCase.getDetailPokemon(id).asLiveData()

}