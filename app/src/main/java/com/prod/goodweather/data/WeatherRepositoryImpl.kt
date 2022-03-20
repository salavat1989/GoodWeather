package com.prod.goodweather.data

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.prod.goodweather.data.database.AppDao
import com.prod.goodweather.data.database.dbmodel.AddressModelDb
import com.prod.goodweather.data.mapper.WeatherMapper
import com.prod.goodweather.data.network.ApiService
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val apiService: ApiService,
	private val mapper: WeatherMapper,
	private val application: Application,
	private val appDao: AppDao,
) : WeatherRepository {
	private val language = application.resources.configuration.locales.get(0).language
	private val scope = CoroutineScope(Dispatchers.IO)
	private var currentAddressModelDb: AddressModelDb? = null

	init {
		scope.launch {
			currentAddressModelDb = appDao.getCurrentLocation()
		}
	}

	override fun getCurrentLocation(): LiveData<AddressModel> {
		return Transformations.map(appDao.getCurrentLocationLive()) {
			it?.let {
				mapper.mapAddressModelDBToAddressModel(it)
			}
		}
	}

	override fun currentLocationChanged(locationModel: LocationModel) {
		val oldLocation = currentAddressModelDb?.let {
			LocationModel(it.latitude, it.longitude)
		}
		if (locationModel.isSmallChange(oldLocation)) {
			scope.launch {
				val address = getAddress(locationModel)
				val mainAddress =
					address.locality ?: address.subAdminArea ?: address.adminArea
					?: address.countryName
					?: ""
				val newAddressDb = AddressModelDb(
					currentAddressModelDb?.id ?: 0,
					mainAddress,
					address.thoroughfare,
					locationModel.latitude,
					locationModel.longitude,
					true
				)
				appDao.insertAddress(newAddressDb)
				currentAddressModelDb = appDao.getCurrentLocation()
				refreshCurrentLocationWeather()
			}
		}
	}

	private fun refreshCurrentLocationWeather() {
		currentAddressModelDb?.let {
			scope.launch {
				val weatherDto = apiService.getWeatherWithForecast(
					lon = it.longitude.toString(),
					lat = it.latitude.toString(),
					lang = language,
				)
				val allWeatherDb = mapper.mapWeatherDtoToAllDb(weatherDto, it.id)
				appDao.insertAllWeather(allWeatherDb)
			}
		}

	}

	private fun getAddress(location: LocationModel): Address {
		if (Geocoder.isPresent()) {
			val geocoder = Geocoder(application)
			val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
			if (addresses.isNotEmpty()) {
				return addresses[0]
			}
			throw RuntimeException("Address isEmpty")
		}
		throw RuntimeException("Geocoder not present")
	}

	override fun getCurrentLocationWeather(): LiveData<Weather> {
		val allWeatherDb = appDao.getCurrentLocationAllWeather()
		return Transformations.map(allWeatherDb) {
			it?.let {
				mapper.mapAllWeatherDbToEntity(it)
			}
		}
	}

	override suspend fun getLocationWeather(location: LocationModel): Weather {
		val weatherDto = apiService.getWeatherWithForecast(
			lon = location.longitude.toString(),
			lat = location.latitude.toString(),
			lang = language,
		)
		return mapper.mapWeatherDtoToEntity(weatherDto)
	}

	override suspend fun getLocationAddress(location: LocationModel): AddressModel {
		if (Geocoder.isPresent()) {
			val geocoder = Geocoder(application)
			val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
			if (addresses.isNotEmpty()) {
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