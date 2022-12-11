package com.example.pokeapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokeApiResponse(
    @Expose @SerializedName("count") val count: Int,
    @Expose @SerializedName("next") val next: String,
    @Expose @SerializedName("previous") val previous: String,
    @Expose @SerializedName("results") val results: List<RegionsResult>

)

data class RegionsResult(
    @Expose @SerializedName("name") val name: String
)

data class InfoRegionResponse(
    @Expose @SerializedName("pokedexes") val pokedexes: List<Pokedexes>
)

data class Pokedexes(
    @Expose @SerializedName("name") val name: String
)

data class PokeDexResponse(
    @Expose @SerializedName("pokemon_entries") val pokemonEntries: List<PokemonEntries>
)

data class PokemonEntries(
    @Expose @SerializedName("pokemon_species") val pokemonSpecies: SpeciesResult
)

data class SpeciesResult(
    @Expose @SerializedName("name") val name: String
)
data class Pokemons(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("order") val numberPokemon: Int?,
    @Expose @SerializedName("types") val types: List<Type>?,
    @Expose @SerializedName("sprites") val sprites: Sprite?,
)

data class Type (
    @Expose @SerializedName("name") val name: String
)
data class Sprite (
    @Expose @SerializedName("other") val sprites: Other
)
data class Other (
    @Expose @SerializedName("dream_world") val default: Default
)
data class Default (
    @Expose @SerializedName("front_default") val pokemonImage: String
)