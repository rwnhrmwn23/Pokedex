package com.onedev.pokedex.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.onedev.pokedex.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM tb_pokemon")
    fun getPokemon(): DataSource.Factory<Int, PokemonEntity>

    @Query("SELECT * FROM tb_pokemon WHERE id = :id")
    fun getPokemonById(id: Int): Flow<PokemonEntity>

    @Query("SELECT * FROM tb_pokemon WHERE pokemon_name LIKE '%' || :pokemonName || '%'")
    fun getPokemonByName(pokemonName: String): DataSource.Factory<Int, PokemonEntity>

    @Query("SELECT * FROM tb_pokemon WHERE pokemon_is_favorite = 1")
    fun getPokemonFavorite(): DataSource.Factory<Int, PokemonEntity>

    @Update
    fun updatePokemonFavorite(pokemonEntity: PokemonEntity)

    @Update
    suspend fun updatePokemon(pokemonEntity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntity: List<PokemonEntity>)

}