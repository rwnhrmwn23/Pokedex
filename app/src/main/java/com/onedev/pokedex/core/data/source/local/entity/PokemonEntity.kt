package com.onedev.pokedex.core.data.source.local.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String,
    @ColumnInfo(name = "pokemon_image") val pokemonImage: String,
    @ColumnInfo(name = "pokemon_height") @Nullable val pokemonHeight: Int? = null,
    @ColumnInfo(name = "pokemon_weight") @Nullable val pokemonWeight: Int? = null,
    @ColumnInfo(name = "pokemon_type") @Nullable val pokemonType: String? = null,
    @ColumnInfo(name = "pokemon_hp") @Nullable val pokemonHp: Int? = null,
    @ColumnInfo(name = "pokemon_atk") @Nullable val pokemonAtk: Int? = null,
    @ColumnInfo(name = "pokemon_def") @Nullable val pokemonDef: Int? = null,
    @ColumnInfo(name = "pokemon_s_atk") @Nullable val pokemonSAtk: Int? = null,
    @ColumnInfo(name = "pokemon_s_def") @Nullable val pokemonSDef: Int? = null,
    @ColumnInfo(name = "pokemon_spd") @Nullable val pokemonSpd: Int? = null,
    @ColumnInfo(name = "pokemon_is_favorite") var pokemonIsFavorite: Boolean = false,
)