package com.prod.goodweather.domain.repository

import com.prod.goodweather.domain.repository.entity.LocationEntity

interface WeatherRepository {
    fun getCurrentLocation(): LocationEntity
}