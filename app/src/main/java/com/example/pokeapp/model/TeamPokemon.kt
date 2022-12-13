package com.example.pokeapp.model

class TeamPokemon(var teamName: String, var pokemons: List<Pokemons>) {
    constructor(): this("", emptyList())
}


