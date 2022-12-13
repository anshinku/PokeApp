package com.example.pokeapp.ui.adapters

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.ui.animations.ListViewAnimatorHelper
import com.example.pokeapp.ui.animations.ReboundAnimator
import com.example.pokeapp.ui.holders.RegionsViewHolder
import com.example.pokeapp.interfaces.interfaceadapter.ClickListener


class RegionsAdapter(
    private val clickListener: ClickListener<RegionsResult>,
    private val context: Activity?,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RegionsViewHolder>() {
    private var animatorViewHelper: ListViewAnimatorHelper? = null
    private var reboundAnimatorManager: ReboundAnimator? = null

    private val regions = mutableListOf<RegionsResult>()
    private var regionIcon: List<String> = emptyList()

    fun setRegionIconList(list: List<String>) {
        regionIcon = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.card_region_item, parent, false
        )
        animatorViewHelper = ListViewAnimatorHelper(
            context, recyclerView.layoutManager as LinearLayoutManager
        )
        reboundAnimatorManager =
            ReboundAnimator(context, ReboundAnimator.ReboundAnimatorType.RIGHT_TO_LEFT)

        return RegionsViewHolder(
            itemView = layout, listener = clickListener
        )

    }

    override fun onBindViewHolder(holder: RegionsViewHolder, position: Int) {
        val region = regions[position]
        val iconList = regionIcon[position]

        holder.item = region
        holder.regionName.text = region.name
        holder.regionIcon.text = iconList

        val animators: Array<Animator?> =
            reboundAnimatorManager!!.getReboundAnimatorForView(holder.itemView.rootView)

        animatorViewHelper!!.animateViewIfNecessary(
            position, holder.itemView, animators
        )
    }

    override fun getItemCount(): Int = regions.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateRegionList(regionsResult: List<RegionsResult>) {
        this.regions.clear()
        this.regions.addAll(regionsResult)
        notifyDataSetChanged()
    }
}
