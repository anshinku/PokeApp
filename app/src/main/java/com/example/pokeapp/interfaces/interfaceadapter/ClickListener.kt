package com.example.pokeapp.interfaces.interfaceadapter

import android.view.View
import com.example.pokeapp.model.Pokemons

interface ClickListener<T> {
    fun onClick(view: View, item: T, position: Int)
}