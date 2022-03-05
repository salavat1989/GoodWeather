package com.prod.goodweather.data.network

import com.prod.goodweather.BuildConfig
import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.data.network.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query(LATITUDE) lat: String,
        @Query(LONGITUDE) lon: String,
        @Query(LANGUAGE) lang: String,
        @Query(UNITS) units: String = METRIC,
        @Query(API_KEY) apiKey: String = BuildConfig.MY_OW_API_KEY,
    ): CurrentWeatherDto

    @GET("onecall")
    suspend fun getWeatherWithForecast(
        @Query(LATITUDE) lat: String,
        @Query(LONGITUDE) lon: String,
        @Query(LANGUAGE) lang: String,
        @Query(UNITS) units: String = METRIC,
        @Query(EXCLUDE) exclude: String = EXCLUDE_VALUE,
        @Query(API_KEY) apiKey: String = BuildConfig.MY_OW_API_KEY,
    ): WeatherDto

    companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"
        private const val API_KEY = "appid"
        private const val LANGUAGE = "lang"
        private const val UNITS = "units"
        private const val METRIC = "metric"
        private const val EXCLUDE = "exclude"
        private const val EXCLUDE_VALUE = "minutely"
    }
}