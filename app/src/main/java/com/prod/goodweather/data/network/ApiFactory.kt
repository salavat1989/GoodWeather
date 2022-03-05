package com.prod.goodweather.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val IMAGE_URL_TEMPLATE = "https://openweathermap.org/img/wn/%s@2x.png"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService =
        retrofit.create(ApiService::class.java) ?: throw RuntimeException("Don't create apiService")
}