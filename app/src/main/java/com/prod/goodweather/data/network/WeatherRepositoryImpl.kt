package com.prod.goodweather.data.network

import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.domain.entity.LocationEntity
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : WeatherRepository {
    override suspend fun getCurrentWeather(locationEntity: LocationEntity): CurrentWeatherDto {
        return apiService.getCurrentWeather(
            lon = locationEntity.longitude.toString(),
            lat = locationEntity.latitude.toString()
        )
    }
}