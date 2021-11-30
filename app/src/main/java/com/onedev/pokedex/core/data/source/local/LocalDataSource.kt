package com.onedev.pokedex.core.data.source.local

import com.onedev.pokedex.core.data.source.local.entity.PokemonEntity
import com.onedev.pokedex.core.data.source.local.room.PokemonDao

class LocalDataSource(private val pokemonDao: PokemonDao) {

    fun getPokemon() = pokemonDao.getPokemon()

    fun getPokemonFavorite() = pokemonDao.getPokemonFavorite()

    fun updatePokemonFavorite(pokemonEntity: PokemonEntity, newState: Boolean) {
        pokemonEntity.pokemonIsFavorite = newState
        pokemonDao.updatePokemonFavorite(pokemonEntity)
    }

    suspend fun insertPokemon(pokemonEntity: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonEntity)

}