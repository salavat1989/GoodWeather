package com.prod.goodweather.domain.entity

data class CurrentWeather(
    val cityName : String,
    val temperature: String,
    val minTemperature:String,
    val maxTemperature:String,
    val weatherDescription :String?,
    val iconUrl:String?,
)
