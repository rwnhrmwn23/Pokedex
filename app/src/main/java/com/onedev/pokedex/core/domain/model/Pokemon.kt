package com.onedev.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val pokemonName: String,
    val pokemonImage: String,
    val pokemonHeight: Int? = null,
    val pokemonWeight: Int? = null,
    val pokemonType: String? = null,
    val pokemonHp: Int? = null,
    val pokemonAtk: Int? = null,
    val pokemonDef: Int? = null,
    val pokemonSAtk: Int? = null,
    val pokemonSDef: Int? = null,
    val pokemonSpd: Int? = null,
    var pokemonIsFavorite: Boolean = false,
): Parcelable