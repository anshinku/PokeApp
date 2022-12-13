package com.example.pokeapp.ui.adapters

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.Pokemons
import com.example.pokeapp.ui.animations.ListViewAnimatorHelper
import com.example.pokeapp.ui.animations.ReboundAnimator
import com.example.pokeapp.ui.holders.PokemonViewHolder
import com.example.pokeapp.utilities.loadUrl


class PokemonAdapter(
    private val context: Activity?,
    private val recyclerView: RecyclerView,
    private val pokemonDescription: String,
) : RecyclerView.Adapter<PokemonViewHolder>() {
    private var animatorViewHelper: ListViewAnimatorHelper? = null
    private var reboundAnimatorManager: ReboundAnimator? = null
    private val pokemons = mutableListOf<Pokemons>()
    private val teamPokemon = mutableListOf<Pokemons>()
    private val pokemonSelected = mutableListOf<Int>()
    private var isEnable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.card_pokemon_item, parent, false
        )
        animatorViewHelper = ListViewAnimatorHelper(
            context, recyclerView.layoutManager as LinearLayoutManager
        )
        reboundAnimatorManager =
            ReboundAnimator(context, ReboundAnimator.ReboundAnimatorType.RIGHT_TO_LEFT)

        return PokemonViewHolder(
            itemView = layout
        )


    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemons = this.pokemons[position]

        val formatterNumber = pokemons.numberPokemon.toString().padStart(3, '0')
        val pokemonNumber = "№ $formatterNumber"

        holder.pokemonName.text = pokemons.name.uppercase()
        holder.pokemonNumber.text = pokemonNumber
        holder.pokemonType.text = pokemons.pokemonTypes[0].type.name
        holder.pokemonDescription.text = context?.getString(R.string.pokemon_description)

        if (pokemons.pokemonTypes.size > 1) {
            holder.pokemonSecondType.visibility = View.VISIBLE
            holder.pokemonSecondType.text = pokemons.pokemonTypes[1].type.name
        } else {
            holder.pokemonSecondType.visibility = View.GONE
        }

        if (pokemons.sprites.sprites.default.pokemonImage != null) {
            holder.pokemonImage.loadUrl(pokemons.sprites.sprites.default.pokemonImage)
        } else {
            holder.pokemonImage.loadUrl(pokemons.sprites.sprites.homeDefault.pokemonImage)
        }

        val animators: Array<Animator?> =
            reboundAnimatorManager!!.getReboundAnimatorForView(holder.itemView.rootView)

        animatorViewHelper!!.animateViewIfNecessary(
            position, holder.itemView, animators
        )

        holder.itemView.setOnLongClickListener {
            selectItem(holder, pokemons, position)
            true
        }

        holder.itemView.setOnClickListener {

            if (pokemonSelected.contains(position)) {
                pokemonSelected.removeAt(position)
                teamPokemon.remove(pokemons)
                holder.pokemonSelected.setImageResource(R.drawable.not_gotcha)
                pokemons.isSelected = false

                if (pokemonSelected.isEmpty()) {
                    isEnable = false
                }

            } else if (isEnable) {
                selectItem(holder, pokemons, position)
            }
        }
    }

    private fun selectItem(holder: PokemonViewHolder, pokemons: Pokemons, position: Int) {
        isEnable = true
        pokemonSelected.add(position)
        teamPokemon.add(pokemons)
        pokemons.isSelected = true
        holder.pokemonSelected.setImageResource(R.drawable.gotcha)
    }

    override fun getItemCount(): Int = pokemons.size

    @SuppressLint("NotifyDataSetChanged")
    fun updatePokemonList(regionsResult: List<Pokemons>) {
        this.pokemons.clear()
        this.pokemons.addAll(regionsResult)
        notifyDataSetChanged()
    }

    fun teamPokemon(): MutableList<Pokemons> {
        return this.teamPokemon
    }

}
