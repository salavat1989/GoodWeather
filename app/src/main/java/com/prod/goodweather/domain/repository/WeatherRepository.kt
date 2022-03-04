package com.prod.goodweather.domain.repository

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.CurrentWeather
import com.prod.goodweather.data.network.model.LocationEntity

interface WeatherRepository {
    suspend fun getCurrentWeather(locationEntity: LocationEntity): CurrentWeather

    fun getCurrentLocationCurrentWeather(): LiveData<CurrentWeather>
}