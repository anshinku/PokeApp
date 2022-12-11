package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.model.PokeApiResponse
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.service.PokeApiService
import constants.ActivityConstants.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegionsListViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder().baseUrl(Url().baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val service: PokeApiService = retrofit.create(PokeApiService::class.java)

    val regionsList = MutableLiveData<List<RegionsResult>>()

    fun getRegionList() {
        val call = service.getRegions()

        call.enqueue(object : Callback<PokeApiResponse> {
            override fun onResponse(
                call: Call<PokeApiResponse>, response: Response<PokeApiResponse>
            ) {
                response.body()?.results?.let { list ->

                    regionsList.postValue(list)
                }
            }

            override fun onFailure(call: Call<PokeApiResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }
}