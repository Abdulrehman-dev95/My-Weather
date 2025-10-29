package com.example.myweather.data

import com.example.myweather.network.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val networkRepository: NetworkRepository
}

class DefaultAppContainer : AppContainer {

    private val retrofit = Retrofit.Builder().baseUrl(
        "https://api.openweathermap.org/data/2.5/"
    ).addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)


    override val networkRepository: NetworkRepository by lazy {

        NetworkRepositoryImpl(api = retrofit)
    }


}