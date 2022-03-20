package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.ViewModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.usecase.CurrentLocationChangedUseCase
import com.prod.goodweather.domain.usecase.GetCurrentLocationUseCase
import com.prod.goodweather.domain.usecase.GetCurrentLocationWeatherUseCase
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
	private val getCurrentLocation: GetCurrentLocationUseCase,
	private val currentLocationChanged: CurrentLocationChangedUseCase,
	private val getCurrentLocationWeather: GetCurrentLocationWeatherUseCase,
) : ViewModel() {
	val address = getCurrentLocation()
	val weather = getCurrentLocationWeather()

	fun locationChanged(locationModel: LocationModel) {
		currentLocationChanged(locationModel)
	}
}
