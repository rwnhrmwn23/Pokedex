package com.onedev.pokedex.core.data.source.remote.response

data class DataPokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: DataStat
)