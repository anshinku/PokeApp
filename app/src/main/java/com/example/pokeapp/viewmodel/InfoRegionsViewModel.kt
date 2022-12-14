package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.domain.GetRegionInfoUseCase
import com.example.pokeapp.model.Pokedexes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoRegionsViewModel : ViewModel() {

    val pokemons = MutableLiveData<Pokedexes?>()
    val isLoading = MutableLiveData<Boolean?>()
    var getRegionInfoUseCase = GetRegionInfoUseCase()

    fun getRegion(name: String): MutableLiveData<Pokedexes?> {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getRegionInfoUseCase(name)

            if (!result?.name.isNullOrEmpty()) {
                isLoading.postValue(false)
                pokemons.postValue(result)
            }
        }
        return pokemons
    }

}