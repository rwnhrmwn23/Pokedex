package com.onedev.pokedex.core.domain.repository

import androidx.paging.PagedList
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import com.onedev.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

    fun getPokemon(limit: Int): Flow<Resource<PagedList<Pokemon>>>
    fun getPokemonById(id: Int): Flow<Resource<Pokemon>>
    fun getPokemonFavorite(): Flow<List<Pokemon>>
    suspend fun updatePokemon(pokemon: Pokemon, state: Boolean)

}