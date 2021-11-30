package com.onedev.pokedex.core.data.source.local

import com.onedev.pokedex.core.data.source.local.entity.PokemonEntity
import com.onedev.pokedex.core.data.source.local.room.PokemonDao

class LocalDataSource(private val pokemonDao: PokemonDao) {

    fun getPokemon() = pokemonDao.getPokemon()

    fun getPokemonById(id: Int) = pokemonDao.getPokemonById(id)

    fun getPokemonFavorite() = pokemonDao.getPokemonFavorite()

    suspend fun updatePokemon(pokemonEntity: PokemonEntity, newState: Boolean) {
        pokemonEntity.pokemonIsFavorite = newState
        pokemonDao.updatePokemon(pokemonEntity)
    }

    suspend fun insertPokemon(pokemonEntity: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonEntity)

}