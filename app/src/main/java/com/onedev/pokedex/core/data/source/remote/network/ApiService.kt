package com.onedev.pokedex.core.data.source.remote.network

import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import com.onedev.pokedex.core.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): PokemonDetailsResponse

}