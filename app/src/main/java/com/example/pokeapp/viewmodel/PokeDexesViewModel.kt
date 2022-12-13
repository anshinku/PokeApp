package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.domain.GetPokemonsUseCase
import com.example.pokeapp.model.Pokemons
import kotlinx.coroutines.launch

class PokeDexesViewModel : ViewModel() {

    val pokemons = MutableLiveData<List<Pokemons>?>()
    val isLoading = MutableLiveData<Boolean>()

    var getPokemonsUseCase = GetPokemonsUseCase()

    fun getAllPokemon(name: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPokemonsUseCase(name)

            if (!result.isNullOrEmpty()) {
                pokemons.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}