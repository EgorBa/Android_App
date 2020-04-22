package com.example.weatherapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface WeatherAPI {
    @GET("forecast/daily")
    fun getWeather(
        @Query("city_id") cityID: String,
        @Query("days") days: String,
        @Query("key") key: String
    ): Call<Week>
}

fun createWeatherAPI(): WeatherAPI {
    val client = OkHttpClient()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.weatherbit.io/v2.0/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val api = retrofit.create(WeatherAPI::class.java)
    return api
}
