package com.example.pokeapp.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.interfaceadapter.ClickListener
import com.example.pokeapp.model.TeamPokemon
import de.hdodenhof.circleimageview.CircleImageView

class TeamPokemonViewHolder(itemView: View, listener: ClickListener<TeamPokemon>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var item: TeamPokemon
    var teamName = itemView.findViewById(R.id.teamName) as TextView
    var countPokemon = itemView.findViewById(R.id.countPokemon) as TextView
    var firstPokemon = itemView.findViewById(R.id.firstPokemon) as ImageView
    var secondPokemon = itemView.findViewById(R.id.secondPokemon) as ImageView
    var thirdPokemon = itemView.findViewById(R.id.thirdPokemon) as ImageView
    var forPokemon = itemView.findViewById(R.id.forPokemon) as ImageView
    var fivePokemon = itemView.findViewById(R.id.fivePokemon) as ImageView
    var sixPokemon = itemView.findViewById(R.id.sixPokemon) as ImageView

    private val clickListener = listener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        clickListener.onClick(v!!, item,  adapterPosition)
    }

}