package com.example.pokeapp.core

import constants.ActivityConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(ActivityConstants.Url().baseUrl).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

}