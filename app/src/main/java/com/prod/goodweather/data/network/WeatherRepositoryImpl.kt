package com.prod.goodweather.data.network

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prod.goodweather.data.mapper.WeatherMapper
import com.prod.goodweather.data.network.model.LocationEntity
import com.prod.goodweather.domain.entity.CurrentWeather
import com.prod.goodweather.domain.repository.WeatherRepository
import com.prod.goodweather.utils.LiveCurrentLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: WeatherMapper,
    private val currentLocation: LiveCurrentLocation,
    private val application: Application,
) : WeatherRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currentLocationCurrentWeather = MutableLiveData<CurrentWeather>()
    private var oldLocation: LocationEntity? = null
    private val language = application.resources.configuration.locales.get(0).language

    init {
        observeLocationUpdate()
    }

    private fun observeLocationUpdate() {
        currentLocation.observeForever {
            if (it.isBigChange(oldLocation)) {
                oldLocation = it
                scope.launch {
                    val z = getCurrentWeather(it)
                    currentLocationCurrentWeather.postValue(z)
                }
            }
        }
    }

    override suspend fun getCurrentWeather(locationEntity: LocationEntity): CurrentWeather {
        val currentWeatherDto = apiService.getCurrentWeather(
            lon = locationEntity.longitude.toString(),
            lat = locationEntity.latitude.toString(),
            lang = language,
        )
        return mapper.mapCurrentWeatherDtoToEntity(currentWeatherDto)
    }

    override fun getCurrentLocationCurrentWeather(): LiveData<CurrentWeather> {
        return currentLocationCurrentWeather
    }
}