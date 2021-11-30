package com.onedev.pokedex.core.domain.usecase

import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.core.domain.repository.IPokemonRepository

class PokemonInteractor(private val iPokemonRepository: IPokemonRepository) : PokemonUseCase {

    override fun getPokemon(limit: Int) = iPokemonRepository.getPokemon(limit)
    override fun getPokemonById(id: Int) = iPokemonRepository.getPokemonById(id)
    override fun getPokemonFavorite() = iPokemonRepository.getPokemonFavorite()
    override fun updatePokemonFavorite(pokemon: Pokemon, state: Boolean) = iPokemonRepository.updatePokemonFavorite(pokemon, state)

}