package com.example.pokeapp.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R

class PokemonViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var pokemonImage = itemView.findViewById(R.id.pokemonImage) as ImageView
    var pokemonSelected = itemView.findViewById(R.id.pokemonSelected) as ImageView
    var pokemonName = itemView.findViewById(R.id.pokemonName) as TextView
    var pokemonNumber = itemView.findViewById(R.id.pokemonNumber) as TextView
    var pokemonType = itemView.findViewById(R.id.pokemonType) as TextView
    var pokemonSecondType = itemView.findViewById(R.id.pokemonSecondType) as TextView
    var pokemonDescription = itemView.findViewById(R.id.pokemonDescription) as TextView


}