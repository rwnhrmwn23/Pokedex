package com.onedev.pokedex.core.data.source

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.onedev.pokedex.core.data.source.local.LocalDataSource
import com.onedev.pokedex.core.data.source.remote.RemoteDataSource
import com.onedev.pokedex.core.data.source.remote.network.ApiResponse
import com.onedev.pokedex.core.data.source.remote.response.DataPokemon
import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.core.domain.repository.IPokemonRepository
import com.onedev.pokedex.utils.ExtMapper.mapDomainToEntity
import com.onedev.pokedex.utils.ExtMapper.mapEntitiesToListDomain
import com.onedev.pokedex.utils.ExtMapper.mapEntityToDomain
import com.onedev.pokedex.utils.ExtMapper.mapResponsesToEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IPokemonRepository {

    override fun getPokemon(limit: Int): Flow<Resource<PagedList<Pokemon>>> =
        object : NetworkBoundResource<PagedList<Pokemon>, List<DataPokemon>>() {
            override fun loadFromDB(): Flow<PagedList<Pokemon>> {
                val data = localDataSource.getPokemon().map { it.mapEntityToDomain() }
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(data, config).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Pokemon>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<DataPokemon>>> {
                return remoteDataSource.getListPokemon(limit)
            }

            override suspend fun saveCallResult(data: List<DataPokemon>) {
                val listPokemon = data.mapResponsesToEntities()
                localDataSource.insertPokemon(listPokemon)
            }

        }.asFlow()

    override fun getDetailPokemon(id: Int): Flow<Resource<PokemonDetailsResponse>> =
        object : NetworkOnlyResource<PokemonDetailsResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<PokemonDetailsResponse>> {
                return remoteDataSource.getDetailPokemon(id)
            }
        }.asFlow()

    override fun getPokemonFavorite(): Flow<List<Pokemon>> {
        return localDataSource.getPokemonFavorite().map { it.mapEntitiesToListDomain() }
    }

    override fun updatePokemonFavorite(pokemon: Pokemon, state: Boolean) {
        val pokemonEntities = pokemon.mapDomainToEntity()
        return localDataSource.updatePokemonFavorite(pokemonEntities, state)
    }
}