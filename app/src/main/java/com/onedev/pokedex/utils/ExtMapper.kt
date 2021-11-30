package com.onedev.pokedex.utils

import com.onedev.pokedex.core.data.source.local.entity.PokemonEntity
import com.onedev.pokedex.core.data.source.remote.response.DataPokemon
import com.onedev.pokedex.core.domain.model.Pokemon

object ExtMapper {
    fun Pokemon.mapDomainToEntity() =
        PokemonEntity(id, pokemonName, pokemonImage, pokemonIsFavorite)

    fun PokemonEntity.mapEntityToDomain() =
        Pokemon(id, pokemonName, pokemonImage, pokemonIsFavorite)

    fun List<PokemonEntity>.mapEntitiesToListDomain(): List<Pokemon> =
        this.map {
            Pokemon(it.id, it.pokemonName, it.pokemonImage, it.pokemonIsFavorite)
        }

    fun List<DataPokemon>.mapResponsesToEntities(): List<PokemonEntity> {
        val pokemonList = ArrayList<PokemonEntity>()
        this.map {
            val pokemonId = it.url.split("/".toRegex()).dropLast(1).last().toInt()
            val pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"

            val pokemones = PokemonEntity(
                id = pokemonId,
                pokemonName = it.name,
                pokemonImage = pokemonImage,
                pokemonIsFavorite = false
            )

            pokemonList.add(pokemones)
        }
        return pokemonList
    }
}