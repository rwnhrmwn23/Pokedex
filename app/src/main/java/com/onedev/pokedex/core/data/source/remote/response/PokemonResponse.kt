package com.onedev.pokedex.core.data.source.remote.response

data class PokemonResponse(
    val count: Int? = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DataPokemon>? = null
)