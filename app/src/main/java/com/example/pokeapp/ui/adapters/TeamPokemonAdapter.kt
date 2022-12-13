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
import com.example.pokeapp.model.Trainer
import com.example.pokeapp.ui.animations.ListViewAnimatorHelper
import com.example.pokeapp.ui.animations.ReboundAnimator
import com.example.pokeapp.ui.holders.TeamPokemonViewHolder


class TeamPokemonAdapter(
    private val clickListener: ClickListener<Trainer>,
    private val context: Activity?,
    private val recyclerView: RecyclerView,
) : RecyclerView.Adapter<TeamPokemonViewHolder>() {
    private var animatorViewHelper: ListViewAnimatorHelper? = null
    private var reboundAnimatorManager: ReboundAnimator? = null

    private val trainer = mutableListOf<Trainer>()

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
        val trainers = trainer[position]
        holder.item = trainers
        holder.teamName.text = trainers.teamPokemon?.uppercase()
        holder.countPokemon.text = trainers.pokemons?.size.toString()

        val animators: Array<Animator?> =
            reboundAnimatorManager!!.getReboundAnimatorForView(holder.itemView.rootView)

        animatorViewHelper!!.animateViewIfNecessary(position, holder.itemView, animators)
    }

    override fun getItemCount(): Int = trainer.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTeamList(trainer: List<Trainer>) {
        this.trainer.clear()
        this.trainer.addAll(trainer)
        notifyDataSetChanged()
    }
}
