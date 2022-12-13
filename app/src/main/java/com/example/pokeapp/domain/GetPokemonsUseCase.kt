package com.example.pokeapp.domain

import com.example.pokeapp.model.Pokemons
import com.example.pokeapp.network.PokemonService

class GetPokemonsUseCase {

    private val repository = PokemonService()

    suspend operator fun invoke(name: String): List<Pokemons>? {
        return repository.getPokedex(name)
    }

}