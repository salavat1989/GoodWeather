package com.prod.goodweather.domain.usecase

import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 20.03.2022
 */

class CurrentLocationChangedUseCase @Inject constructor(private val repository: WeatherRepository) {
	operator fun invoke(locationModel: LocationModel) {
		repository.currentLocationChanged(locationModel)
	}
}