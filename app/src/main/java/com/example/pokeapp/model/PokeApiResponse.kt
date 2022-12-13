package com.example.pokeapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
    @Expose @SerializedName("pokemon_species") val pokemons: Pokemons,
)


@Parcelize
data class Pokemons(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("order") val numberPokemon: Int,
    @Expose @SerializedName("types") val pokemonTypes: List<PokemonTypeSlot>,
    @Expose @SerializedName("sprites") val sprites: Sprite,
    @Expose @SerializedName("flavor_text_entries") val flavorText: FlavorText,
    var isSelected: Boolean

) : Parcelable
{
    constructor(): this("", 0, emptyList(), Sprite(), FlavorText(""), false)
}

@Parcelize
data class FlavorText(
    val flavorText: String
) : Parcelable

@Parcelize
data class PokemonTypeSlot(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("type") val type: PokemonType,
) : Parcelable {
    constructor(): this("", PokemonType())
}

@Parcelize
data class PokemonType(
    @Expose @SerializedName("name") val name: String
) : Parcelable
{
    constructor(): this("")
}

@Parcelize
data class Sprite(
    @Expose @SerializedName("other") val sprites: Other
) : Parcelable {
    constructor(): this(Other())
}

@Parcelize
data class Other(
    @Expose @SerializedName("dream_world") val default: Default,
    @Expose @SerializedName("home") val homeDefault: HomeDefault
) : Parcelable
{
    constructor(): this(Default(), HomeDefault())
}

@Parcelize
data class Default(
    @Expose @SerializedName("front_default") val pokemonImage: String?
) : Parcelable
{
    constructor(): this("")
}

@Parcelize
data class HomeDefault(
    @Expose @SerializedName("front_default") val pokemonImage: String
) : Parcelable
{
    constructor(): this("")
}