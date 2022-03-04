package com.prod.goodweather.data.network

import com.prod.goodweather.BuildConfig
import com.prod.goodweather.data.network.model.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query(LATITUDE) lat: String,
        @Query(LONGITUDE) lon: String,
        @Query(API_KEY) apiKey: String = BuildConfig.MY_OW_API_KEY,
    ) : CurrentWeatherDto

    companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"
        private const val API_KEY = "appid"
    }
}