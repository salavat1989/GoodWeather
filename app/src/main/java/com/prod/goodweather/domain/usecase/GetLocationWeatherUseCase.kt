package com.prod.goodweather.domain.usecase

import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocationWeatherUseCase
@Inject
constructor(
	private val repository: WeatherRepository,
) {
	suspend operator fun invoke(location: LocationModel): Weather {
		return repository.getLocationWeather(location)
	}
}