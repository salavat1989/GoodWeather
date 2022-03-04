package com.prod.goodweather.domain.repository

import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.domain.entity.LocationEntity

interface WeatherRepository {
    suspend fun getCurrentWeather(locationEntity: LocationEntity): CurrentWeatherDto
}