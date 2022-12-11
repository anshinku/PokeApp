package com.example.pokeapp.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.ui.interfacebuttons.ClickListener

class PokemonViewHolder(itemView: View, listener: ClickListener<RegionsResult>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var item: RegionsResult
    var pokemonImage = itemView.findViewById(R.id.pokemonImage) as ImageView
    var pokemonName = itemView.findViewById(R.id.pokemonName) as TextView

    private val clickListener = listener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        clickListener.onClick(v!!, item,  adapterPosition)
    }

}