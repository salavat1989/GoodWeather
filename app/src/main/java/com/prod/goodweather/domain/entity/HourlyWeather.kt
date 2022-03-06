package com.prod.goodweather.domain.entity

data class HourlyWeather(
    val forecastTime: String,
    val temperature: String,
    val iconURL: String?,
)