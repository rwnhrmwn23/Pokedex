package com.onedev.pokedex.core.domain.usecase

import androidx.paging.PagedList
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun getPokemon(limit: Int): Flow<Resource<PagedList<Pokemon>>>
    fun getPokemonByName(pokemonByName: String): Flow<PagedList<Pokemon>>
    fun getPokemonById(id: Int): Flow<Resource<Pokemon>>
    fun getPokemonFavorite(): Flow<PagedList<Pokemon>>
    fun updatePokemonFavorite(pokemon: Pokemon, state: Boolean)

}