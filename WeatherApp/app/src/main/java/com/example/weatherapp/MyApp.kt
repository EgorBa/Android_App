package com.example.weatherapp

import android.app.Application

class MyApp : Application() {
    lateinit var weatherAPI: WeatherAPI
        private set

    override fun onCreate() {
        super.onCreate()
        val api = createWeatherAPI()
        weatherAPI = api
        app = this
    }

    companion object {
        lateinit var app: MyApp
            private set
    }

}

