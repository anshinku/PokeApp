package com.example.pokeapp.network

import androidx.lifecycle.MutableLiveData
import com.example.pokeapp.core.RetrofitHelper
import com.example.pokeapp.model.Pokedexes
import com.example.pokeapp.model.RegionsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegionService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(PokeApiClient::class.java)

    suspend fun getAllRegion(): List<RegionsResult>? {
        val regionsList = MutableLiveData<List<RegionsResult>>()

        withContext(Dispatchers.IO) {
            val call = service.getRegions()

            val regions = call.body()?.results

           regionsList.postValue(regions!!)

        }.let {
            return regionsList.value
        }

    }
}