package com.example.myweather.data.model

import com.example.myweather.model.Clouds
import com.example.myweather.model.Coord
import com.example.myweather.model.Main
import com.example.myweather.model.Sys
import com.example.myweather.model.Weather
import com.example.myweather.model.Wind

data class WeatherData(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)