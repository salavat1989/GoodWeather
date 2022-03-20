package com.prod.goodweather.domain.usecase

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 19.03.2022
 */

class GetCurrentLocationWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
	operator fun invoke(): LiveData<Weather> {
		return repository.getCurrentLocationWeather()
	}
}
