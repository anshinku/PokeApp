package com.example.pokeapp.network

import com.example.pokeapp.core.RetrofitHelper
import com.example.pokeapp.model.Pokedexes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegionInfoService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(PokeApiClient::class.java)

    suspend fun getPokedex(name: String): Pokedexes {
        val pokedexes = mutableListOf<Pokedexes>()

        withContext(Dispatchers.IO) {
            val call = service.getRegionInfo(name)

            val pokedex = call.body()!!.pokedexes
            pokedexes.add(pokedex[0])

        }.let {
            return pokedexes.first()
        }

    }
}