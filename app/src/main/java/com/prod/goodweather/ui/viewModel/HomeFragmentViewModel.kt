package com.prod.goodweather.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.usecase.GetLocationAddressUseCase
import com.prod.goodweather.domain.usecase.GetLocationWeatherUseCase
import com.prod.goodweather.utils.LiveCurrentLocation
import com.prod.goodweather.utils.checkAccessFineLocationPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
	private val application: Application,
	private val currentLocation: LiveCurrentLocation,
	private val getWeather: GetLocationWeatherUseCase,
	private val getLocationAddress: GetLocationAddressUseCase,
) : ViewModel() {
	init {
		checkAndWaitPermission()
	}

	private val _address = MutableLiveData<AddressModel>()
	val address: LiveData<AddressModel>
		get() = _address
	private val _weather = MutableLiveData<Weather>()
	val weather: LiveData<Weather>
		get() = _weather

	private var oldLocation: LocationModel? = null

	private fun checkAndWaitPermission() {
		if ((application as Context).checkAccessFineLocationPermission()) {
			observeLocationUpdate()
		} else {
			viewModelScope.launch {
				delay(5000)
				checkAndWaitPermission()
			}
		}
	}

	private fun observeLocationUpdate() {
		currentLocation.observeForever {
			if (it.isSmallChange(oldLocation)) {
				viewModelScope.launch(Dispatchers.IO) {
					val address = getLocationAddress(it)
					_address.postValue(address)
				}
				if (it.isBigChange(oldLocation)) {
					oldLocation = it
					viewModelScope.launch(Dispatchers.IO) {
						val weather = getWeather(it)
						_weather.postValue(weather)
					}
				}
			}
		}
	}
}
