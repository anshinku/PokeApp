package com.example.pokeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.model.PokeDexResponse
import com.example.pokeapp.model.PokemonEntries
import com.example.pokeapp.service.PokeApiService
import constants.ActivityConstants.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeDexesViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder().baseUrl(Url().baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val service = retrofit.create(PokeApiService::class.java)

    val pokemonEntries = MutableLiveData<List<PokemonEntries>>()

    fun getPokedex(regionName: String) {
        val call = service.getPokeDex(regionName)

        call.enqueue(object : Callback<PokeDexResponse> {
            override fun onResponse(
                call: Call<PokeDexResponse>, response: Response<PokeDexResponse>
            ) {
                response.body()?.pokemonEntries?.let { list ->
                    pokemonEntries.postValue(list)
                }
            }

            override fun onFailure(call: Call<PokeDexResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }
}