package com.prod.goodweather.domain.usecase

import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.domain.entity.LocationEntity
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(locationEntity: LocationEntity): CurrentWeatherDto {
        return repository.getCurrentWeather(locationEntity)
    }
}