package com.prod.goodweather.domain.entity

data class DailyWeather(
    val unixTime:Long,
    val forecastTime: String,
    val temperatureMin: String,
    val temperatureMax: String,
    val iconURL: String?,
)