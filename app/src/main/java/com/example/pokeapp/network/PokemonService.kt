package com.example.pokeapp.network

import com.example.pokeapp.core.RetrofitHelper
import com.example.pokeapp.model.Pokemons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(PokeApiClient::class.java)

    suspend fun getPokedex(regionName: String): List<Pokemons> {
        val pokemons = mutableListOf<Pokemons>()

        withContext(Dispatchers.IO) {
            val call = service.getPokeDex(regionName)

            val pokedex = call.body()
            if (pokedex != null) {
                for (pokemon in pokedex.pokemonEntries) {
                    val pokemonDetails = getPokemon((pokemon.pokemons.name))
                    pokemonDetails?.let { pokemons.add(it) }
                }
            }

        }.let {
            return pokemons
        }

    }

    private suspend fun getPokemon(name: String): Pokemons? {
        return service.getPokemon(name).body()

    }
}