package com.example.pokeapp.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.interfaces.interfaceadapter.ClickListener
import com.example.pokeapp.model.Pokemons
import com.example.pokeapp.model.Trainer
import de.hdodenhof.circleimageview.CircleImageView

class TeamPokemonViewHolder(itemView: View, listener: ClickListener<Trainer>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var item: Trainer
    var teamName = itemView.findViewById(R.id.teamName) as TextView
    var countPokemon = itemView.findViewById(R.id.countPokemon) as TextView

    private val clickListener = listener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        clickListener.onClick(v!!, item,  adapterPosition)
    }

}