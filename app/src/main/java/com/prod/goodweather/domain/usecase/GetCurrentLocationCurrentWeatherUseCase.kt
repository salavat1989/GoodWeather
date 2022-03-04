package com.prod.goodweather.domain.usecase

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.CurrentWeather
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentLocationCurrentWeatherUseCase
@Inject
constructor(
    private val repository: WeatherRepository,
) {
    operator fun invoke(): LiveData<CurrentWeather> {
        return repository.getCurrentLocationCurrentWeather()
    }
}