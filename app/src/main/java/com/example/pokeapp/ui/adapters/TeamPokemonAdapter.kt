package com.example.pokeapp.ui.adapters

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.interfaceadapter.ClickListener
import com.example.pokeapp.model.TeamPokemon
import com.example.pokeapp.ui.animations.ListViewAnimatorHelper
import com.example.pokeapp.ui.animations.ReboundAnimator
import com.example.pokeapp.ui.holders.TeamPokemonViewHolder
import com.example.pokeapp.utilities.loadUrl


class TeamPokemonAdapter(
    private val clickListener: ClickListener<TeamPokemon>,
    private val context: Activity?,
    private val recyclerView: RecyclerView,
) : RecyclerView.Adapter<TeamPokemonViewHolder>() {
    private var animatorViewHelper: ListViewAnimatorHelper? = null
    private var reboundAnimatorManager: ReboundAnimator? = null

    private val teamPokemon = mutableListOf<TeamPokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPokemonViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_team_item, parent, false)
        animatorViewHelper =
            ListViewAnimatorHelper(context, recyclerView.layoutManager as LinearLayoutManager)
        reboundAnimatorManager =
            ReboundAnimator(context, ReboundAnimator.ReboundAnimatorType.RIGHT_TO_LEFT)

        return TeamPokemonViewHolder(itemView = layout, listener = clickListener)

    }

    override fun onBindViewHolder(holder: TeamPokemonViewHolder, position: Int) {
        val team = teamPokemon[position]

        holder.item = team
        holder.teamName.text = team.teamName.uppercase()
        holder.countPokemon.text = team.pokemons.size.toString()
        team.pokemons[0].sprites.sprites.default.pokemonImage?.let { holder.firstPokemon.loadUrl(it) }


        val animators: Array<Animator?> =
            reboundAnimatorManager!!.getReboundAnimatorForView(holder.itemView.rootView)

        animatorViewHelper!!.animateViewIfNecessary(position, holder.itemView, animators)
    }

    override fun getItemCount(): Int = teamPokemon.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTeamList(teamPokemon: List<TeamPokemon>) {
        this.teamPokemon.clear()
        this.teamPokemon.addAll(teamPokemon)
        notifyDataSetChanged()
    }
}
