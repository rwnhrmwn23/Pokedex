package com.onedev.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val pokemonName: String,
    val pokemonImage: String,
    var pokemonIsFavorite: Boolean = false,
): Parcelable