package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.domain.GetRegionInfoUseCase
import com.example.pokeapp.domain.GetRegionUseCase
import com.example.pokeapp.model.RegionsResult
import kotlinx.coroutines.launch

class RegionsListViewModel : ViewModel() {
    val regions = MutableLiveData<List<RegionsResult>?>()

    var getRegionUseCase = GetRegionUseCase()

    fun getRegion() {
        viewModelScope.launch {
            val result = getRegionUseCase()

            if (!result.isNullOrEmpty()) {
                regions.postValue(result)
            }
        }
    }

}