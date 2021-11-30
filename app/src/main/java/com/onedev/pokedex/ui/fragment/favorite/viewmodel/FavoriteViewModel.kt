package com.onedev.pokedex.ui.fragment.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.onedev.pokedex.core.data.source.PokemonRepository
import com.onedev.pokedex.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    fun getPokemonFavorite() = pokemonUseCase.getPokemonFavorite().asLiveData()

}