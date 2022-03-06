package com.prod.goodweather.domain.entity

data class Weather(
    val cityName: String?,
    val temperature: String,
    val feelsLike: String,
    val weatherDescription: String?,
    val iconUrl: String?,
    val listHourlyWeather: List<HourlyWeather>
)
