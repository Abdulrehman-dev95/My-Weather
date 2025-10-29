package com.example.myweather

import android.app.Application
import com.example.myweather.data.AppContainer
import com.example.myweather.data.DefaultAppContainer

class Application: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }

}