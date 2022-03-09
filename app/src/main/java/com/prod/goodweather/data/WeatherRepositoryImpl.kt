package com.prod.goodweather.data

import android.app.Application
import android.location.Address
import android.location.Geocoder
import com.prod.goodweather.data.mapper.WeatherMapper
import com.prod.goodweather.data.network.ApiService
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val apiService: ApiService,
	private val mapper: WeatherMapper,
	private val application: Application,
) : WeatherRepository {
	private val language = application.resources.configuration.locales.get(0).language

	suspend override fun getLocationWeather(location: LocationModel): Weather {
		val weatherDto = apiService.getWeatherWithForecast(
			lon = location.longitude.toString(),
			lat = location.latitude.toString(),
			lang = language,
		)
		return mapper.mapWeatherDtoToEntity(weatherDto)
	}

	suspend override fun getLocationAddress(location: LocationModel): AddressModel {
		if (Geocoder.isPresent()) {
			val geocoder = Geocoder(application)
			val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
			if (!addresses.isEmpty()) {
				val address: Address = addresses[0]

				return mapper.mapAddressToAddressModel(address)
			}
			throw RuntimeException("Address isEmpty")
		}
		throw RuntimeException("Geocoder not present")
	}

	override suspend fun getLocationsFromSubString(subString: String): List<AddressModel> {
		if (Geocoder.isPresent()) {
			val geocoder = Geocoder(application)
			return geocoder.getFromLocationName(subString, 5).map {
				mapper.mapAddressToAddressModel(it)
			}
		}
		return emptyList()
	}
}