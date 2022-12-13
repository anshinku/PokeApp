package com.example.pokeapp.network

import com.example.pokeapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiClient {

    @GET("region/{name}/")
    suspend fun getRegionInfo(@Path("name") name: String): Response<InfoRegionResponse>

    @GET("region")
    suspend fun getRegions(): Response<PokeApiResponse>

    @GET("pokedex/{name}/")
    suspend fun getPokeDex(@Path("name") regionName: String): Response<PokeDexResponse>

    @GET("pokemon/{name}/")
    suspend fun getPokemon(@Path("name") pokemonName: String): Response<Pokemons>

    @GET("pokemon-species/{name}/")
    fun getPokemonDescription(@Path("name") pokemonName: String): retrofit2.Call<FlavorText>
}