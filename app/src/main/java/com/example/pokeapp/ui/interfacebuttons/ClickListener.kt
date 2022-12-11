package com.example.pokeapp.ui.interfacebuttons

import android.view.View

interface ClickListener<T> {
    fun onClick(view: View, item: T, position: Int)
}