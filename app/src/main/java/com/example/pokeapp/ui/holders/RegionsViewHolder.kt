package com.example.pokeapp.ui.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.ui.interfacebuttons.ClickListener

class RegionsViewHolder(itemView: View, listener: ClickListener<RegionsResult>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var item: RegionsResult
    var regionName = itemView.findViewById(R.id.regionName) as TextView
    var regionIcon = itemView.findViewById(R.id.iconRegion) as TextView

    private val clickListener = listener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        clickListener.onClick(v!!, item,  adapterPosition)
    }

}