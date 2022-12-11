package com.example.pokeapp.service

import com.example.pokeapp.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("region/{name}/")
    fun getRegionInfo(@Path("name") name: String): retrofit2.Call<InfoRegionResponse>

    @GET("region")
    fun getRegions(): retrofit2.Call<PokeApiResponse>

    @GET("pokedex/{name}/")
    fun getPokeDex(@Path("name") regionName: String): retrofit2.Call<PokeDexResponse>

}