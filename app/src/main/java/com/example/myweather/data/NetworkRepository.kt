package com.example.myweather.data

import android.util.Log
import com.example.myweather.model.WeatherData
import com.example.myweather.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NetworkRepository {
    fun getWeatherData(
        cityName: String,
        apiKey: String,
        units: String,
        onResult: (Result<WeatherData>) -> Unit
    )
}


class NetworkRepositoryImpl(private val api: Api) : NetworkRepository {
    override fun getWeatherData(
        cityName: String,
        apiKey: String,
        units: String, onResult: (Result<WeatherData>) -> Unit
    ) {
        val api = api.getWeatherData(city = cityName, apiKey = apiKey, units = units)
        api.enqueue(object : Callback<WeatherData> {
            override fun onResponse(
                call: Call<WeatherData?>,
                response: Response<WeatherData?>
            ) {
                val weatherData = response.body()
                if (response.isSuccessful && weatherData != null) {
                    val temp = weatherData.main.temp
                    Log.d("WeatherData", "Temperature: $temp")
                    onResult(Result.success(value = weatherData))
                } else {
                    onResult(Result.failure(Exception("Failed to fetch weather data")))
                }
            }

            override fun onFailure(
                call: Call<WeatherData?>,
                t: Throwable
            ) {
                onResult(Result.failure(t))
            }

        })

    }

}