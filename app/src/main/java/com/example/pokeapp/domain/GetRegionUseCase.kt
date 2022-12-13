package com.example.pokeapp.domain

import com.example.pokeapp.model.Pokedexes
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.network.RegionInfoService
import com.example.pokeapp.network.RegionService

class GetRegionUseCase {

    private val repository = RegionService()

    suspend operator fun invoke(): List<RegionsResult>? {
        return repository.getAllRegion()
    }

}