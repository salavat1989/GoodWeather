package com.prod.goodweather.domain.repository

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather

interface WeatherRepository {

	fun getCurrentLocation(): LiveData<AddressModel>

	fun currentLocationChanged(locationModel: LocationModel)

	fun getCurrentLocationWeather(): LiveData<Weather>

	suspend fun getLocationAddress(location: LocationModel): AddressModel

	suspend fun getLocationWeather(location: LocationModel): Weather

	suspend fun getLocationsFromSubString(subString: String): List<AddressModel>
}