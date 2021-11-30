package com.onedev.pokedex.core.data.source.remote.response

data class PokemonDetailsResponse(
    val height: Int,
    val id: Int,
    val name: String,
    val stats: List<DataPokemonStat>,
    val types: List<DataPokemonType>,
    val weight: Int
)