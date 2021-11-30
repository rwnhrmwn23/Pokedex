package com.onedev.pokedex.utils

import com.onedev.pokedex.core.data.source.local.entity.PokemonEntity
import com.onedev.pokedex.core.data.source.remote.response.DataPokemon
import com.onedev.pokedex.core.data.source.remote.response.PokemonDetailsResponse
import com.onedev.pokedex.core.domain.model.Pokemon

object ExtMapper {
    fun Pokemon.mapDomainToEntity() =
        PokemonEntity(id, pokemonName, pokemonImage, pokemonHeight, pokemonWeight, pokemonType, pokemonHp, pokemonAtk, pokemonDef, pokemonSAtk, pokemonSDef, pokemonSpd, pokemonIsFavorite)

    fun PokemonEntity.mapEntityToDomain() =
        Pokemon(id, pokemonName, pokemonImage, pokemonHeight, pokemonWeight, pokemonType, pokemonHp, pokemonAtk, pokemonDef, pokemonSAtk, pokemonSDef, pokemonSpd, pokemonIsFavorite)

    fun List<PokemonEntity>.mapEntitiesToListDomain(): List<Pokemon> =
        this.map {
            Pokemon(it.id, it.pokemonName, it.pokemonImage, it.pokemonHeight, it.pokemonWeight, it.pokemonType, it.pokemonHp, it.pokemonAtk, it.pokemonDef, it.pokemonSAtk, it.pokemonSDef, it.pokemonSpd,  it.pokemonIsFavorite)
        }

    fun List<DataPokemon>.mapResponsesToEntities(): List<PokemonEntity> {
        val pokemonList = ArrayList<PokemonEntity>()
        this.map {
            val pokemonId = it.url.split("/".toRegex()).dropLast(1).last().toInt()
            val pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"

            val pokemones = PokemonEntity(
                id = pokemonId,
                pokemonName = it.name,
                pokemonImage = pokemonImage
            )

            pokemonList.add(pokemones)
        }
        return pokemonList
    }

    fun PokemonDetailsResponse.mapResponsesDetailToEntities(): PokemonEntity {
        with(this) {
            val pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

            val pokemonType = StringBuilder().append("")
            for (i in types.indices) {
                if (i < types.size - 1)
                    pokemonType.append("${types[i].type.name}, ")
                else
                    pokemonType.append(types[i].type.name)
            }

            return PokemonEntity(
                id = id,
                pokemonName = name,
                pokemonImage = pokemonImage,
                pokemonHeight = height,
                pokemonWeight = weight,
                pokemonType = pokemonType.toString(),
                pokemonHp = stats[0].base_stat,
                pokemonAtk = stats[1].base_stat,
                pokemonDef = stats[2].base_stat,
                pokemonSAtk = stats[3].base_stat,
                pokemonSDef = stats[4].base_stat,
                pokemonSpd = stats[5].base_stat,
            )
        }
    }
}