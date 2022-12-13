package com.example.pokeapp.domain

import com.example.pokeapp.model.Pokedexes
import com.example.pokeapp.network.RegionInfoService

class GetRegionInfoUseCase {

    private val repository = RegionInfoService()

    suspend operator fun invoke(name: String):Pokedexes? {
        return repository.getPokedex(name)
    }

}