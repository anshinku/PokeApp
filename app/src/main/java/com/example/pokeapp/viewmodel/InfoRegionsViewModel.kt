package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.model.InfoRegionResponse
import com.example.pokeapp.model.Pokedexes
import com.example.pokeapp.service.PokeApiService
import constants.ActivityConstants.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InfoRegionsViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder().baseUrl(Url().baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val service: PokeApiService = retrofit.create(PokeApiService::class.java)

    val regionInfo = MutableLiveData<Pokedexes>()

    fun getRegion(name: String) {

        val call = service.getRegionInfo(name)

        call.enqueue(object : Callback<InfoRegionResponse> {
            override fun onResponse(
                call: Call<InfoRegionResponse>, response: Response<InfoRegionResponse>
            ) {
                response.body()?.pokedexes?.let { list ->
                    regionInfo.postValue(list.last())
                }
            }

            override fun onFailure(call: Call<InfoRegionResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }

}