package com.prod.goodweather.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.prod.goodweather.data.mapper.WeatherMapper
import com.prod.goodweather.data.network.ApiService
import com.prod.goodweather.data.network.model.LocalityAddressDto
import com.prod.goodweather.data.network.model.LocationEntity
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.data.worker.AddressFromGeocoderWorker
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.entity.Weather
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
    private val currentLocationWeather = MutableLiveData<WeatherDto>()
    private var oldLocation: LocationEntity? = null
    private var address = MutableLiveData<LocalityAddressDto>()
    private val language = application.resources.configuration.locales.get(0).language

    init {
        observeLocationUpdate()
    }

    private fun observeLocationUpdate() {
        currentLocation.observeForever {
            if (it.isSmallChange(oldLocation)) {
                findAddressFromLocation(it)
                if (it.isBigChange(oldLocation)) {
                    oldLocation = it
                    scope.launch {
                        val z = getCurrentWeather(it)
                        currentLocationWeather.postValue(z)
                    }
                }
            }
        }
    }

    private suspend fun getCurrentWeather(locationEntity: LocationEntity): WeatherDto {
        return apiService.getWeatherWithForecast(
            lon = locationEntity.longitude.toString(),
            lat = locationEntity.latitude.toString(),
            lang = language,
        )
    }

    override fun getCurrentLocationWeather(): LiveData<Weather> {
        return Transformations.map(currentLocationWeather) {
            mapper.mapWeatherDtoToEntity(it)
        }
    }

    override fun getCurrentLocationAddress(): LiveData<LocalityAddress> {
        return Transformations.map(address) {
            mapper.mapLocalityAddressToEntity(it)
        }
    }

    private fun findAddressFromLocation(locationEntity: LocationEntity) {
        val workManager = WorkManager.getInstance(application)
        val oneTimeWorkRequest =
            AddressFromGeocoderWorker.makeRequest(locationEntity.latitude, locationEntity.longitude)
        workManager.enqueueUniqueWork(
            AddressFromGeocoderWorker.WORK_NAME,
            ExistingWorkPolicy.APPEND,
            oneTimeWorkRequest
        )
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observeForever {
            if (it.state.isFinished) {
                val dtoAddress = LocalityAddressDto(
                    it.outputData.getString(AddressFromGeocoderWorker.COUNTRY_NAME) ?: "UNKNOWN",
                    it.outputData.getString(AddressFromGeocoderWorker.ADMIN_AREA),
                    it.outputData.getString(AddressFromGeocoderWorker.SUB_ADMIN_AREA),
                    it.outputData.getString(AddressFromGeocoderWorker.LOCALITY),
                    it.outputData.getString(AddressFromGeocoderWorker.THOROUGHFARE)
                )
                address.postValue(dtoAddress)
            }
        }
    }
}