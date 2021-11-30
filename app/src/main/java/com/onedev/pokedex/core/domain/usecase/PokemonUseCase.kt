package com.onedev.pokedex.core.domain.usecase

import androidx.paging.PagedList
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import com.onedev.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun getPokemon(limit: Int): Flow<Resource<PagedList<Pokemon>>>
    fun getPokemonById(id: Int): Flow<Resource<Pokemon>>
    fun getPokemonFavorite(): Flow<List<Pokemon>>
    fun updatePokemonFavorite(pokemon: Pokemon, state: Boolean)

}