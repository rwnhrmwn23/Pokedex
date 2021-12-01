package com.onedev.pokedex.core.data.source.remote

import com.onedev.pokedex.core.data.source.remote.network.ApiResponse
import com.onedev.pokedex.core.data.source.remote.network.ApiService
import com.onedev.pokedex.core.data.source.remote.response.DataPokemon
import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getListPokemon(limit: Int): Flow<ApiResponse<List<DataPokemon>>> {
        return flow {
            try {
                val response = apiService.getListPokemon(limit)
                val dataArray = response.results
                if (dataArray != null)
                    emit(ApiResponse.Success(dataArray))
                else
                    emit(ApiResponse.Error("Something When Wrong..."))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("getListPokemon: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailPokemon(id: Int): Flow<ApiResponse<PokemonDetailsResponse>> {
        return flow {
            try {
                val response = apiService.getDetailPokemon(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("getDetailPokemon: $e")
            }
        }.flowOn(Dispatchers.IO)
    }
}